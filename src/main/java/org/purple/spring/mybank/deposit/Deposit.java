package org.purple.spring.mybank.deposit;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Deposit {
	@Id
	@GeneratedValue
	private Long id;
	private Long duration;
	private String currency;
	private Double interestRate;

	public Deposit() {

	}

	public Deposit(Long duration, String currency, Double interestRate) {
		this.duration = duration;
		this.currency = currency;
		this.interestRate = interestRate;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
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

	@Override
	public int hashCode() {
		return Objects.hash(currency, duration, id, interestRate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Deposit other = (Deposit) obj;
		return Objects.equals(currency, other.currency) && Objects.equals(duration, other.duration)
				&& Objects.equals(id, other.id) && Objects.equals(interestRate, other.interestRate);
	}

	@Override
	public String toString() {
		return "Deposit [id=" + id + ", duration=" + duration + ", currency=" + currency + ", interestRate="
				+ interestRate + "]";
	}
}
