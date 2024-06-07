package org.purple.spring.mybank.account;

import java.util.Calendar;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Account {
	private String currency;
	private Long amount;
	@Id
	private String iban;
	private String tin;
	private String ownerFullName;
	private Calendar creationDate;

	public Account() {

	}
	
	public Account(String iban) {
		this.iban = iban;
	}

	public Account(String currency, Long amount, String iban, String tin, String ownerFullName, Calendar creationDate,
			String address, String phone, String owner) {
		super();
		this.currency = currency;
		this.amount = amount;
		this.iban = iban;
		this.tin = tin;
		this.ownerFullName = ownerFullName;
		this.creationDate = creationDate;
		this.address = address;
		this.phone = phone;
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, amount, creationDate, currency, iban, owner, ownerFullName, phone, tin);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(address, other.address) && Objects.equals(amount, other.amount)
				&& Objects.equals(creationDate, other.creationDate) && Objects.equals(currency, other.currency)
				&& Objects.equals(iban, other.iban) && Objects.equals(owner, other.owner)
				&& Objects.equals(ownerFullName, other.ownerFullName) && Objects.equals(phone, other.phone)
				&& Objects.equals(tin, other.tin);
	}

	@Override
	public String toString() {
		return "Account [currency=" + currency + ", amount=" + amount + ", iban=" + iban + ", tin=" + tin
				+ ", ownerFullName=" + ownerFullName + ", creationDate=" + creationDate + ", address=" + address
				+ ", phone=" + phone + ", owner=" + owner + "]";
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getOwnerFullName() {
		return ownerFullName;
	}

	public void setOwnerFullName(String ownerFullName) {
		this.ownerFullName = ownerFullName;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	private String address;
	private String phone;
	private String owner; // username
}
