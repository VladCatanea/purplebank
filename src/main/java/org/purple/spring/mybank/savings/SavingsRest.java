package org.purple.spring.mybank.savings;

import java.util.List;

import org.purple.spring.mybank.errors.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SavingsRest {
	private final SavingsRepository repository;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public SavingsRest(SavingsRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping(value = "/api/savings", produces = "application/json")
	public ResponseEntity<List<Savings>> listSavings() {
		logger.info("Returning list of all savings");
		return new ResponseEntity<>(repository.findAll(), HttpStatus.FOUND);
	}
	
	@GetMapping(value = "/api/savings/{id}")
	public ResponseEntity<Savings> oneSavings(@PathVariable Long id){
		Savings savings = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "savings"));
		return new ResponseEntity<>(savings, HttpStatus.FOUND);
	}
	
	
}
