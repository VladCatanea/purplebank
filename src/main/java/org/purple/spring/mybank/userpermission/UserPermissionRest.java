package org.purple.spring.mybank.userpermission;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserPermissionRest {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping(value = "/api/permission", produces = "application/json")
	public ResponseEntity<String> getPermission(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		logger.info("Returning user permission: {}", userDetails);
		Collection<? extends GrantedAuthority> user = userDetails.getAuthorities();
		Boolean adminPermission = user.contains(new SimpleGrantedAuthority("ROLE_USER"));
		String permission;
		if (adminPermission) {
			permission = "ADMIN";
		}
		else {
			permission = "USER";
		}
		return new ResponseEntity<>(permission, HttpStatus.FOUND);
	}
	
}
