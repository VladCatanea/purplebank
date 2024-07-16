package org.purple.spring.mybank.account;


import static org.purple.spring.mybank.Constants.BASE_API;

import java.util.List;

import org.purple.spring.mybank.errors.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BASE_API + "/accountTransaction")
public class AccountTransactionRest {
	private static final Logger logger = LoggerFactory.getLogger(AccountTransactionRest.class);
	private TransactionAssignedRepository transactionsRepository;
	private AccountRepository accountRepository;
	
	AccountTransactionRest(TransactionAssignedRepository transactionsRepository, AccountRepository accountRepository){
		this.transactionsRepository = transactionsRepository;
		this.accountRepository = accountRepository;
	}
	
	@GetMapping("/{receiverIban}")
	public ResponseEntity<List<ATransaction>> getTransactions(@PathVariable String receiverIban, Authentication authentication){
		String username = authentication.getName();
		logger.debug("Searching transaction list for username {} and account {}", username, receiverIban);
		accountRepository.findByIbanAndOwner(receiverIban, username).orElseThrow(() -> new EntityNotFoundException(receiverIban, "Account"));
		List<ATransaction> transactionList = transactionsRepository.findByReceiverIban(receiverIban);
		logger.debug("Returning transaction list for user {}: {}", username, transactionList);
		return new ResponseEntity<>(transactionList, HttpStatus.FOUND);
	}
}
