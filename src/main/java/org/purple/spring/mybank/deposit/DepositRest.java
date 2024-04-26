package org.purple.spring.mybank.deposit;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepositRest {
	private final DepositRepository repository;

	public DepositRest(DepositRepository repository) {
		this.repository = repository;
	}

	@GetMapping(value = "/deposits", produces = "application/json")
	public List<Deposit> getDeposits() {
		return repository.findAll();
	}

	@PostMapping(value = "/deposits", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Deposit> createDeposit(@RequestBody Deposit deposit) {
		repository.save(deposit);
		return new ResponseEntity<>(deposit, HttpStatus.CREATED);
	}

	@PutMapping(value = "/deposits/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Deposit> updateDeposit(@RequestBody Deposit newDeposit, @PathVariable Long id) {
		repository.findById(id).map(deposit -> {
			deposit.setDuration(newDeposit.getDuration());
			deposit.setCurrency(newDeposit.getCurrency());
			return repository.save(deposit);
		}).orElseGet(() -> {
			newDeposit.setId(id);
			return repository.save(newDeposit);
		});
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(value = "deposits/{id}")
	public ResponseEntity<Deposit> deleteDeposit(@PathVariable Long id){
		repository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}