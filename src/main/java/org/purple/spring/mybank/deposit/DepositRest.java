package org.purple.spring.mybank.deposit;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	public Deposit createDeposit(@RequestBody Deposit deposit) {
		return repository.save(deposit);
	}

}