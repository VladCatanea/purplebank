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
import org.springframework.stereotype.Component;

@Component
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
		accountRepository.save(new Account("RON", 20L, "RO66BACX0000001234567890", "1900103417536", "Dorel Popescu", Calendar.getInstance(), "Bucuresti, Romania", "+40393020391", USER));
		accountRepository.save(new Account("GBP", 100L, "GB33BUKB20201555555555", "1900103417536", "Dorel Popescu", Calendar.getInstance(), "Bucuresti, Romania", "+40393020391", USER));
	}
}
