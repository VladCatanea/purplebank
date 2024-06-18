package org.purple.spring.mybank.account;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
	List<Account> findByOwner(String owner);
	Optional<Account> findByIbanAndOwner(String iban, String owner);
}
