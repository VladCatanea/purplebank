package org.purple.spring.mybank.savings;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Savings {
	@Id
	@GeneratedValue
	Long id;
	Long depositId;
	Long amount;
	String owner;
	Date expiration;

	public Savings() {

	}

	public Savings(Long depositId, Long amount, String owner, Date expiration) {
		super();
		this.depositId = depositId;
		this.amount = amount;
		this.owner = owner;
		this.expiration = expiration;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, depositId, expiration, id, owner);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Savings other = (Savings) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(depositId, other.depositId)
				&& Objects.equals(expiration, other.expiration) && Objects.equals(id, other.id)
				&& Objects.equals(owner, other.owner);
	}

	@Override
	public String toString() {
		return "Savings [id=" + id + ", depositId=" + depositId + ", amount=" + amount + ", owner=" + owner
				+ ", expiration=" + expiration + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDepositId() {
		return depositId;
	}

	public void setDepositId(Long depositId) {
		this.depositId = depositId;
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

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}
}
