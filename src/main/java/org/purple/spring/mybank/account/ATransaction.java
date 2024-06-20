package org.purple.spring.mybank.account;

import java.util.Calendar;
import java.util.Objects;

import org.purple.spring.mybank.transactions.Transaction;
import org.purple.spring.mybank.util.Utils;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ATransaction {
	@Id
	private String referenceNum;
	private String receiverIban;
	private String senderIban;
	private String senderName;
	private Calendar transactionDate;
	private String authorizationCode = null; // optional
	private Long debitAmount;
	private Long creditAmount;

	public ATransaction() {
		super();
	}
	
	public ATransaction(Transaction t) {
		super();
		this.referenceNum = t.getReferenceNum();
		this.receiverIban = t.getReceiverIban();
		this.senderIban = t.getSenderIban();
		this.senderName = t.getSenderIban();
		this.debitAmount = t.getDebitAmount();
		this.creditAmount = t.getCreditAmount();
		this.transactionDate = Utils.stringToCal(t.getTransactionDate());
	}

	public ATransaction(String referenceNum, String receiverIban, String senderIban, String senderName,
			Calendar transactionDate, Long debitAmount, Long creditAmount) {
		super();
		this.referenceNum = referenceNum;
		this.receiverIban = receiverIban;
		this.senderIban = senderIban;
		this.senderName = senderName;
		this.transactionDate = transactionDate;
		this.debitAmount = debitAmount;
		this.creditAmount = creditAmount;
	}

	public ATransaction(String referenceNum, String receiverIban, String senderIban, String senderName,
			Calendar transactionDate, String authorizationCode, Long debitAmount, Long creditAmount) {
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

	public Calendar getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Calendar transactionDate) {
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
		ATransaction other = (ATransaction) obj;
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
