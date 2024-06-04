package org.purple.spring.mybank;

import static org.purple.spring.mybank.Constants.USER;

import java.util.Calendar;

import org.purple.spring.mybank.deposit.Deposit;
import org.purple.spring.mybank.deposit.DepositRepository;
import org.purple.spring.mybank.savings.Savings;
import org.purple.spring.mybank.savings.SavingsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
	@Bean
	int initDatabase(DepositRepository depositRepository, SavingsRepository savingsRepository) {
		depositRepository.save(new Deposit(100L, "RON", 3.5));
		depositRepository.save(new Deposit(365L, "EUR", 50.0));
		savingsRepository.save(new Savings(1L, 20L, USER, Calendar.getInstance()));
		return 1;
	}
}
