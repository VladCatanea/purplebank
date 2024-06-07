package org.purple.spring.mybank;

import static org.purple.spring.mybank.Constants.USER;

import java.util.Calendar;

import org.purple.spring.mybank.account.Account;
import org.purple.spring.mybank.account.AccountRepository;
import org.purple.spring.mybank.deposit.Deposit;
import org.purple.spring.mybank.deposit.DepositRepository;
import org.purple.spring.mybank.savings.Savings;
import org.purple.spring.mybank.savings.SavingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase implements ApplicationRunner {
	
	@Autowired
	private DepositRepository depositRepository;
	@Autowired
	private SavingsRepository savingsRepository;
	@Autowired
	private AccountRepository accountRepository;
	
	public void run(ApplicationArguments args) {
		depositRepository.save(new Deposit(100L, "RON", 3.5));
		depositRepository.save(new Deposit(365L, "EUR", 50.0));
		savingsRepository.save(new Savings(1L, 20L, USER, Calendar.getInstance()));
		accountRepository.save(new Account("1"));
	}
}
