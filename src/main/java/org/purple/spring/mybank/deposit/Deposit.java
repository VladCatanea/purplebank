package org.purple.spring.mybank.deposit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Deposit {
	@Id
	@GeneratedValue
	Long id;
	Long duration;
	String currency;

	public Deposit() {

	}

	public Deposit(Long duration, String currency) {
		this.duration = duration;
		this.currency = currency;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
