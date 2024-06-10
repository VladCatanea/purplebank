package org.purple.spring.mybank.account;

import static org.purple.spring.mybank.Constants.BASE_API;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BASE_API + "/account")
public class AccountRest {
	
	@Autowired
	AccountRepository accountRepository;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping
	public ResponseEntity<List<Account>> getAccountInfo(Authentication authentication){
		List<Account> accountList = accountRepository.findByOwner(authentication.getName());
		logger.debug("Returning account list: {}", accountList);
		return new ResponseEntity<>(accountList, HttpStatus.FOUND);
	}
}
