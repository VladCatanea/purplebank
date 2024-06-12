package org.purple.spring.mybank.transactions;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Transaction {
	@Id
	String referenceNum;
	String receiverIban;
	String senderIban;
	String senderName;
	String transactionDate;
	String authorizationCode = null; //optional
	Long debitAmount;
	Long creditAmount;
	
	
	public Transaction() {
		super();
	}
	
	public Transaction(String referenceNum, String receiverIban, String senderIban, String senderName,
			String transactionDate, Long debitAmount, Long creditAmount) {
		super();
		this.referenceNum = referenceNum;
		this.receiverIban = receiverIban;
		this.senderIban = senderIban;
		this.senderName = senderName;
		this.transactionDate = transactionDate;
		this.debitAmount = debitAmount;
		this.creditAmount = creditAmount;
	}




	public Transaction(String referenceNum, String receiverIban, String senderIban, String senderName,
			String transactionDate, String authorizationCode, Long debitAmount, Long creditAmount) {
		super();
		this.referenceNum = referenceNum;
		this.receiverIban = receiverIban;
		this.senderIban = senderIban;
		this.senderName = senderName;
		this.transactionDate = transactionDate;
		this.authorizationCode = authorizationCode;
		this.debitAmount = debitAmount;
		this.creditAmount = creditAmount;
	}




	public String getReferenceNum() {
		return referenceNum;
	}




	public void setReferenceNum(String referenceNum) {
		this.referenceNum = referenceNum;
	}




	public String getReceiverIban() {
		return receiverIban;
	}




	public void setReceiverIban(String receiverIban) {
		this.receiverIban = receiverIban;
	}




	public String getSenderIban() {
		return senderIban;
	}




	public void setSenderIban(String senderIban) {
		this.senderIban = senderIban;
	}




	public String getSenderName() {
		return senderName;
	}




	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}




	public String getTransactionDate() {
		return transactionDate;
	}




	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}




	public String getAuthorizationCode() {
		return authorizationCode;
	}




	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}




	public Long getDebitAmount() {
		return debitAmount;
	}




	public void setDebitAmount(Long debitAmount) {
		this.debitAmount = debitAmount;
	}




	public Long getCreditAmount() {
		return creditAmount;
	}




	public void setCreditAmount(Long creditAmount) {
		this.creditAmount = creditAmount;
	}




	@Override
	public int hashCode() {
		return Objects.hash(authorizationCode, creditAmount, debitAmount, receiverIban, referenceNum, senderIban,
				senderName, transactionDate);
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		return Objects.equals(authorizationCode, other.authorizationCode)
				&& Objects.equals(creditAmount, other.creditAmount) && Objects.equals(debitAmount, other.debitAmount)
				&& Objects.equals(receiverIban, other.receiverIban) && Objects.equals(referenceNum, other.referenceNum)
				&& Objects.equals(senderIban, other.senderIban) && Objects.equals(senderName, other.senderName)
				&& Objects.equals(transactionDate, other.transactionDate);
	}




	@Override
	public String toString() {
		return "Transaction [referenceNum=" + referenceNum + ", receiverIban=" + receiverIban + ", senderIban="
				+ senderIban + ", senderName=" + senderName + ", transactionDate=" + transactionDate
				+ ", authorizationCode=" + authorizationCode + ", debitAmount=" + debitAmount + ", creditAmount="
				+ creditAmount + "]";
	}
	
	
}
