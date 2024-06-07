package org.purple.spring.mybank.account;

import static org.purple.spring.mybank.Constants.BASE_API;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BASE_API + "/account")
public class AccountRest {
	@GetMapping
	public ResponseEntity<Account> getAccountInfo(){
		Account account = new Account();
		return new ResponseEntity<>(account, HttpStatus.FOUND);
	}
}
