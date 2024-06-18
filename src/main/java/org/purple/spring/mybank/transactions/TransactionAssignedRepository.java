package org.purple.spring.mybank.transactions;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionAssignedRepository extends JpaRepository<Transaction, String> {
	List<Transaction> findBySenderIban(String senderIban);
}
