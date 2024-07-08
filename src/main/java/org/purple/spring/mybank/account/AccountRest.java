package org.purple.spring.mybank.account;

import static org.purple.spring.mybank.Constants.BASE_API;

import java.util.List;

import org.purple.spring.mybank.errors.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BASE_API + "/account")
public class AccountRest {

	@Autowired
	private AccountRepository accountRepository;

	private static final Logger logger = LoggerFactory.getLogger(AccountRest.class);

	@GetMapping
	public ResponseEntity<List<Account>> getAccountInfo(Authentication authentication) {
		List<Account> accountList = accountRepository.findByOwner(authentication.getName());
		logger.debug("Returning account list for user {}: {}", authentication.getName(), accountList);
		return new ResponseEntity<>(accountList, HttpStatus.FOUND);
	}

	@GetMapping("/{iban}")
	public ResponseEntity<Account> getOneAccountInfo(@PathVariable String iban, Authentication authentication) {
		Account account = accountRepository.findByIbanAndOwner(iban, authentication.getName())
				.orElseThrow(() -> new EntityNotFoundException(iban, "account"));
		logger.debug("Returning account for user {}: {}", authentication.getName(), account);
		return new ResponseEntity<>(account, HttpStatus.FOUND);
	}
}
