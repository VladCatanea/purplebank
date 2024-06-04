package org.purple.spring.mybank.savings;

import java.util.ArrayList;
import java.util.List;

import org.purple.spring.mybank.deposit.Deposit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Component
public class SavingsRepositoryImpl implements SavingsDetailsRepository {
	@Autowired
	EntityManager em;
	
	public List<SavingsDetails> findDetailsByOwner(String owner){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
		Root<Savings> savings = cq.from(Savings.class);
		Root<Deposit> deposit = cq.from(Deposit.class);
		List<Predicate> predicates = new ArrayList<>(); 
		predicates.add(cb.equal(savings.get("owner"), owner));
		predicates.add(cb.equal(savings.get("depositId"), deposit.get("id")));
		cq.where(predicates.toArray(new Predicate[0]));
		cq.multiselect(savings, deposit);
		List<Tuple> result = em.createQuery(cq).getResultList();
		System.out.println(result);
		List<SavingsDetails> SavingsDetailsList = result.stream().map(obj -> new SavingsDetails((Savings) obj.get(0), (Deposit) obj.get(1))).toList();
		return SavingsDetailsList;
	}
}
