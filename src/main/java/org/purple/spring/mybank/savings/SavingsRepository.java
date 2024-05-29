package org.purple.spring.mybank.savings;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

public interface SavingsRepository extends JpaRepository<Savings, Long> {
	List<Savings> findByOwner(String owner);
	Optional<Savings> findByIdAndOwner(Long id, String owner);
	@Transactional
	void removeByIdAndOwner(Long id, String owner);
}