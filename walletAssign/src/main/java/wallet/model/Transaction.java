package wallet.model;

import java.util.Date;

public class Transaction {
	
	private int id;
	private int amount;
	private String transactionType;
	private Date timeStamp;
	
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", amount=" + amount + ", transactionType=" + transactionType + ", timeStamp="
				+ timeStamp + "]";
	}
	
	
}
