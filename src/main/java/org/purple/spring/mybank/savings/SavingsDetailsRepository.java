package org.purple.spring.mybank.savings;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface SavingsDetailsRepository {
	List<SavingsDetails> findDetailsByOwner(String owner);
}
