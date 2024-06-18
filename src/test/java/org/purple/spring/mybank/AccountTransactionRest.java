package org.purple.spring.mybank;


import static org.purple.spring.mybank.Constants.BASE_API;

import java.util.List;

import org.purple.spring.mybank.account.AccountRepository;
import org.purple.spring.mybank.errors.EntityNotFoundException;
import org.purple.spring.mybank.transactions.Transaction;
import org.purple.spring.mybank.transactions.TransactionAssignedRepository;
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
	private Logger logger = LoggerFactory.getLogger(getClass());
	private TransactionAssignedRepository transactionsRepository;
	private AccountRepository accountRepository;
	
	AccountTransactionRest(TransactionAssignedRepository transactionsRepository, AccountRepository accountRepository){
		this.transactionsRepository = transactionsRepository;
		this.accountRepository = accountRepository;
	}
	
	@GetMapping("/{senderIban}")
	public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String senderIban, Authentication authentication){
		String username = authentication.getName();
		logger.debug("Searching transaction list for username {} and account {}", username, senderIban);
		accountRepository.findByIbanAndOwner(senderIban, username).orElseThrow(() -> new EntityNotFoundException(senderIban, "Account"));
		List<Transaction> transactionList = transactionsRepository.findBySenderIban(senderIban);
		logger.debug("Returning transaction list: {}", transactionList);
		return new ResponseEntity<>(transactionList, HttpStatus.FOUND);
	}
}
