package org.purple.spring.mybank;

import org.purple.spring.mybank.deposit.Deposit;
import org.purple.spring.mybank.deposit.DepositRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
	@Bean
	int initDatabase(DepositRepository repository) {
		repository.save(new Deposit(365L, "EUR"));
		repository.save(new Deposit(100L, "RON"));
		return 1;
	}
}
