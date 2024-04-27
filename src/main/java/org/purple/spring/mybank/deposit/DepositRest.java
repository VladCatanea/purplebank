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
	
	  @GetMapping("/deposits/{id}")
	  ResponseEntity<Deposit> one(@PathVariable Long id) {
	    try {
	    Deposit deposit = repository.findById(id)
	      .orElseThrow(() -> new DepositNotFoundException(id));
	    return new ResponseEntity<>(deposit, HttpStatus.FOUND);
	    }
	    catch(Exception DepositNotFoundException){
	    	return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	    }
	  }

	
	@PostMapping(value = "/deposits", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Long> createDeposit(@RequestBody Deposit deposit) {
		deposit = repository.save(deposit);
		return new ResponseEntity<>(deposit.getId(), HttpStatus.CREATED);
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