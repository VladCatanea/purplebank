package org.purple.spring.mybank.savings;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Savings {
	@Id
	@GeneratedValue
	Long id;
	String currency;
	Long duration;
	Long amount;

	public Savings(String currency, Long duration, Long amount) {
		super();
		this.currency = currency;
		this.duration = duration;
		this.amount = amount;
	}
	
	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof Savings))
			return false;
		Savings savings = (Savings) o;
		return Objects.equals(this.id, savings.id) && Objects.equals(this.duration, savings.duration)
				&& Objects.equals(this.currency, savings.currency) && Objects.equals(this.amount, savings.amount);
	}

	@Override
	public String toString() {
		return "Savings [id=" + id + ", duration=" + duration + ", currency=" + currency + ", amount=" + amount + "]";
	}


	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
}
