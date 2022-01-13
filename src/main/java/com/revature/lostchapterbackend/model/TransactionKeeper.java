package com.revature.lostchapterbackend.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TransactionKeeper {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int transactionId;
	private String orderNumber;
	private double totalPrice;
	@ElementCollection
	private List<String> previousOrder;

	public TransactionKeeper() {
		super();
	}

	public TransactionKeeper(String orderNumber, double totalPrice, List<String> previousOrder) {
		super();
		this.orderNumber = orderNumber;
		this.totalPrice = totalPrice;
		this.previousOrder = previousOrder;
	}
	
	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<String> getPreviousOrder() {
		return previousOrder;
	}

	public void setPreviousOrder(List<String> previousOrder) {
		this.previousOrder = previousOrder;
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderNumber, previousOrder, totalPrice, transactionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionKeeper other = (TransactionKeeper) obj;
		return Objects.equals(orderNumber, other.orderNumber) && Objects.equals(previousOrder, other.previousOrder)
				&& Double.doubleToLongBits(totalPrice) == Double.doubleToLongBits(other.totalPrice)
				&& transactionId == other.transactionId;
	}

	@Override
	public String toString() {
		return "TransactionKeeper [transactionId=" + transactionId + ", orderNumber=" + orderNumber + ", totalPrice="
				+ totalPrice + ", previousOrder=" + previousOrder + "]";
	}

}
