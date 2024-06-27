package org.purple.spring.mybank.savings;

import static org.purple.spring.mybank.Constants.BASE_API;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.purple.spring.mybank.account.ATransaction;
import org.purple.spring.mybank.account.Account;
import org.purple.spring.mybank.account.AccountRepository;
import org.purple.spring.mybank.account.TransactionAssignedRepository;
import org.purple.spring.mybank.deposit.Deposit;
import org.purple.spring.mybank.deposit.DepositRepository;
import org.purple.spring.mybank.errors.EntityNotFoundException;
import org.purple.spring.mybank.errors.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BASE_API + "/savings")
public class SavingsRest {
	private final SavingsRepository savingsRepository;
	private final DepositRepository depositRepository;
	private final AccountRepository accountRepository;
	private final TransactionAssignedRepository transactionRepository;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public SavingsRest(SavingsRepository savingsRepository, DepositRepository depositRepository, AccountRepository accountRepository, TransactionAssignedRepository transactionRepository) {
		this.savingsRepository = savingsRepository;
		this.depositRepository = depositRepository;
		this.accountRepository = accountRepository;
		this.transactionRepository = transactionRepository;
	}

	@GetMapping
	public ResponseEntity<List<Savings>> listSavings(Authentication authentication) {
		String username = authentication.getName();
		logger.debug("Returning list of all savings for user {}", username);
		List<Savings> savingsList = savingsRepository.findByOwner(username);
		logger.debug("List of all savings: {}", savingsList);
		return new ResponseEntity<>(savingsList, HttpStatus.FOUND);
	}
	
	@GetMapping(value = "/details")
	public ResponseEntity<List<SavingsDetails>> listSavingsDetails(Authentication authentication) {
		String username = authentication.getName();
		logger.debug("Returning list of all savings for user {}", username);
		List<SavingsDetails> savingsList = savingsRepository.findDetailsByOwner(username);
		logger.debug("List of all savings details: {}", savingsList);
		return new ResponseEntity<>(savingsList, HttpStatus.FOUND);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Savings> oneSavings(@PathVariable Long id, Authentication authentication) {
		String owner = authentication.getName();
		Savings savings = savingsRepository.findByIdAndOwner(id, owner).orElseThrow(() -> new EntityNotFoundException(id, "savings"));
		logger.debug("Returning savings {}", savings);
		return new ResponseEntity<>(savings, HttpStatus.FOUND);
	}

	@PostMapping
	public ResponseEntity<Long> createSavings(@RequestBody Savings savings, @RequestParam String iban, Authentication authentication) {
		logger.debug("User {} attempts to create deposit", authentication.getName());
		savings.setOwner(authentication.getName());
		Long depositId = savings.getDepositId();
		Deposit deposit = depositRepository.findById(savings.getDepositId())
				.orElseThrow(() -> new EntityNotFoundException(depositId, "deposit"));
		Account account = accountRepository.findById(iban)
				.orElseThrow(() -> new EntityNotFoundException(iban, "account"));
		if (savings.getAmount() > account.getAmount()) {
			throw new TransactionException("Insufficient funds to open savings " + savings + " from account " + iban);
		}
		if (!deposit.getCurrency().equals(account.getCurrency())) {
			throw new TransactionException("Currency mismatch between savings " + savings + "of type " + deposit + " and account " + iban);
		}
		account.setAmount(account.getAmount() - savings.getAmount());
		accountRepository.save(account);
		Calendar calendar = Calendar.getInstance();
		String referenceNum = UUID.randomUUID().toString();
		ATransaction transaction = new ATransaction(referenceNum, iban, iban, account.getOwnerFullName(), calendar, 0L, savings.getAmount());
		calendar.add(Calendar.DATE, (int) (long) deposit.getDuration());
		savings.setExpiration(calendar);
		savings = savingsRepository.save(savings);
		transactionRepository.save(transaction);
		logger.debug("User created savings {}", savings);
		return new ResponseEntity<>(savings.getId(), HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> editSavings(@RequestBody Savings newSavings, @PathVariable Long id, Authentication authentication) {
		newSavings.setOwner(authentication.getName());
		Long depositId = newSavings.getDepositId();
		depositRepository.findById(depositId)
				.orElseThrow(() -> new EntityNotFoundException(depositId, "deposit"));
		savingsRepository.findByIdAndOwner(id, authentication.getName()).map(savings -> {
			return savingsRepository.save(newSavings);
		}).orElseThrow(() -> new EntityNotFoundException(id, "savings"));
		logger.debug("User edited savings {}", newSavings);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteSavings(@PathVariable Long id, Authentication authentication){
		String owner = authentication.getName();
		logger.debug("Deleting Savings with id {} and owner {}", id, owner);
		savingsRepository.removeByIdAndOwner(id, owner);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	

}
