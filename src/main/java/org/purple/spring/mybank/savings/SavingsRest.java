package org.purple.spring.mybank.savings;

import java.util.Calendar;
import java.util.List;

import org.purple.spring.mybank.deposit.Deposit;
import org.purple.spring.mybank.deposit.DepositRepository;
import org.purple.spring.mybank.errors.EntityNotFoundException;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SavingsRest {
	private final SavingsRepository savingsRepository;
	private final DepositRepository depositRepository;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public SavingsRest(SavingsRepository savingsRepository, DepositRepository depositRepository) {
		this.savingsRepository = savingsRepository;
		this.depositRepository = depositRepository;
	}

	@GetMapping(value = "/api/savings", produces = "application/json")
	public ResponseEntity<List<Savings>> listSavings(Authentication authentication) {
		String username = authentication.getName();
		logger.info("Returning list of all savings");
		return new ResponseEntity<>(savingsRepository.findByOwner(username), HttpStatus.FOUND);
	}

	@GetMapping(value = "/api/savings/{id}")
	public ResponseEntity<Savings> oneSavings(@PathVariable Long id, Authentication authentication) {
		String owner = authentication.getName();
		logger.debug("Searching for savings with owner {} and id {}", owner, id);
		Savings savings = savingsRepository.findByIdAndOwner(id, owner).orElseThrow(() -> new EntityNotFoundException(id, "savings"));
		logger.debug("Returning savings with owner {} and id {}", owner, id);
		return new ResponseEntity<>(savings, HttpStatus.FOUND);
	}

	@PostMapping(value = "/api/savings", produces = "application/json")
	public ResponseEntity<Long> createSavings(@RequestBody Savings savings, Authentication authentication) {
		logger.debug("User attempts to create deposit");
		savings.setOwner(authentication.getName());
		Long depositId = savings.getDepositId();
		Deposit deposit = depositRepository.findById(savings.getDepositId())
				.orElseThrow(() -> new EntityNotFoundException(depositId, "deposit"));
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, (int) (long) deposit.getDuration());
		savings.setExpiration(calendar);
		savings = savingsRepository.save(savings);
		logger.info("User {} created savings with id {} and expiration date {}", savings.getOwner(), savings.getId(), savings.getExpiration());
		return new ResponseEntity<>(savings.getId(), HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/api/savings/{id}")
	public ResponseEntity<Void> editSavings(@RequestBody Savings newSavings, @PathVariable Long id, Authentication authentication) {
		newSavings.setOwner(authentication.getName());
		Long depositId = newSavings.getDepositId();
		depositRepository.findById(depositId)
				.orElseThrow(() -> new EntityNotFoundException(depositId, "deposit"));
		savingsRepository.findByIdAndOwner(id, authentication.getName()).map(savings -> {
			return savingsRepository.save(newSavings);
		}).orElseThrow(() -> new EntityNotFoundException(id, "savings"));
		logger.info("User {} edited savings with id {}", newSavings.getOwner(), newSavings.getId());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(value = "/api/savings/{id}")
	public ResponseEntity<Void> deleteSavings(@PathVariable Long id, Authentication authentication){
		String owner = authentication.getName();
		logger.info("Deleting Savings with id {} and owner {}", id, owner);
		savingsRepository.removeByIdAndOwner(id, owner);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	

}
