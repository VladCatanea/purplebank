package org.purple.spring.mybank.transactions;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<Transaction, String> {
	
}
