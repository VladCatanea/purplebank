package org.purple.spring.mybank.transactions;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<Transaction, String> {
	List<Transaction> findAllByOrderByTransactionDateAsc(Pageable pageable);
}
