package org.purple.spring.mybank.deposit;

import java.util.List;
import static org.purple.spring.mybank.Constants.BASE_API;

import org.purple.spring.mybank.errors.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BASE_API + "/deposits")
public class DepositRest {
	private final DepositRepository repository;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public DepositRest(DepositRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping
	public ResponseEntity<List<Deposit>> listDeposits() {
		logger.info("Returning list of all deposits");
		return new ResponseEntity<>(repository.findAll(), HttpStatus.FOUND);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Deposit> getDeposit(@PathVariable Long id) {
		logger.info("Searching for deposit with id {}", id);
		Deposit deposit = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "deposit"));
		logger.info("Returning deposit with id {}", id);
		return new ResponseEntity<>(deposit, HttpStatus.FOUND);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping
	public ResponseEntity<Long> createDeposit(@RequestBody Deposit deposit) {
		deposit = repository.save(deposit);
		logger.info("Created deposit with id {}", deposit.getId());
		return new ResponseEntity<>(deposit.getId(), HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> updateDeposit(@RequestBody Deposit newDeposit, @PathVariable Long id) {
		logger.info("Updating deposit with id {}", id);
		repository.findById(id).map(deposit -> {
			deposit.setDuration(newDeposit.getDuration());
			deposit.setCurrency(newDeposit.getCurrency());
			deposit.setInterestRate(newDeposit.getInterestRate());
			return repository.save(deposit);
		}).orElseThrow(() -> new EntityNotFoundException(id, "deposit"));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteDeposit(@PathVariable Long id) {
		logger.info("Deleting deposit with id {}", id);
		repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}