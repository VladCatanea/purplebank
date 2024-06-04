package org.purple.spring.mybank.savings;

import java.util.Calendar;
import java.util.Objects;

import org.purple.spring.mybank.deposit.Deposit;

public class SavingsDetails {
	private Long id;
	private String currency;
	private Double interestRate;
	private Long amount;
	private String owner;
	private Calendar expiration;
	
	public SavingsDetails() {
		
	}

	public SavingsDetails(Long id, String currency, Double interestRate, Long amount, String owner, Calendar expiration) {
		super();
		this.id = id;
		this.currency = currency;
		this.interestRate = interestRate;
		this.amount = amount;
		this.owner = owner;
		this.expiration = expiration;
	}
	
	public SavingsDetails(Savings savings, Deposit deposit) {
		super();
		this.id = savings.getId();
		this.currency = deposit.getCurrency();
		this.interestRate = deposit.getInterestRate();
		this.amount = savings.getAmount();
		this.owner = savings.getOwner();
		this.expiration = savings.getExpiration();
		}

	@Override
	public int hashCode() {
		return Objects.hash(amount, currency, expiration, id, interestRate, owner);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SavingsDetails other = (SavingsDetails) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(currency, other.currency)
				&& Objects.equals(expiration, other.expiration) && Objects.equals(id, other.id)
				&& Objects.equals(interestRate, other.interestRate) && Objects.equals(owner, other.owner);
	}

	@Override
	public String toString() {
		return "SavingsDetails [id=" + id + ", currency=" + currency + ", interestRate=" + interestRate + ", amount="
				+ amount + ", owner=" + owner + ", expiration=" + expiration + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Calendar getExpiration() {
		return expiration;
	}

	public void setExpiration(Calendar expiration) {
		this.expiration = expiration;
	}
}
