package org.purple.spring.mybank.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserPermissionRest {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping(value = "/api/permission", produces = "application/json")
	public ResponseEntity<Permission> getPermission(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();	
		Boolean adminPermission = userDetails != null && userDetails.getAuthorities().stream()
			      .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
		Permission permission = new Permission();
		if (adminPermission) {
			permission.setPermission("ADMIN");
		}
		else {
			permission.setPermission("USER");
		}
		logger.debug("Returning user permission: {} for user: {}", permission, userDetails);
		return new ResponseEntity<>(permission, HttpStatus.FOUND);
	}
	
}
