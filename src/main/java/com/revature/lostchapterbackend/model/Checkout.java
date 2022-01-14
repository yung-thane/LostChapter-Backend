package com.revature.lostchapterbackend.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Checkout {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int checkoutId;

	@Column(length = 19)
	private String cardNumber; // 13 - 19 restriction
	@Column(length = 4)
	private String securityCode; // 3 - 4 restriction
	@Column(length = 2)
	private String expirationMonth;
	@Column(length = 2)
	private String expirationYear;
	@Column(length = 50)
	private String cardholderName;
	private double cardBalance;
	@ManyToOne
	private ShippingInformation shippingAddress;

	public Checkout() {
		super();
	}

	public Checkout(String cardNumber, String securityCode, String expirationMonth, String expirationYear,
			String cardholderName, double cardBalance, ShippingInformation shippingAddress) {
		super();
		this.cardNumber = cardNumber;
		this.securityCode = securityCode;
		this.expirationMonth = expirationMonth;
		this.expirationYear = expirationYear;
		this.cardholderName = cardholderName;
		this.cardBalance = cardBalance;
		this.shippingAddress = shippingAddress;
	}

	public int getCheckoutId() {
		return checkoutId;
	}

	public void setCheckoutId(int checkoutId) {
		this.checkoutId = checkoutId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getExpirationMonth() {
		return expirationMonth;
	}

	public void setExpirationMonth(String expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public String getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
	}

	public String getCardholderName() {
		return cardholderName;
	}

	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}

	public double getCardBalance() {
		return cardBalance;
	}

	public void setCardBalance(double cardBalance) {
		this.cardBalance = cardBalance;
	}

	public ShippingInformation getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(ShippingInformation shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cardBalance, cardNumber, cardholderName, checkoutId, expirationMonth, expirationYear,
				securityCode, shippingAddress);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Checkout other = (Checkout) obj;
		return Double.doubleToLongBits(cardBalance) == Double.doubleToLongBits(other.cardBalance)
				&& Objects.equals(cardNumber, other.cardNumber) && Objects.equals(cardholderName, other.cardholderName)
				&& checkoutId == other.checkoutId && Objects.equals(expirationMonth, other.expirationMonth)
				&& Objects.equals(expirationYear, other.expirationYear)
				&& Objects.equals(securityCode, other.securityCode) && Objects.equals(shippingAddress, other.shippingAddress);
	}

	@Override
	public String toString() {
		return "Checkout [checkoutId=" + checkoutId + ", cardNumber=" + cardNumber + ", securityCode=" + securityCode
				+ ", expirationMonth=" + expirationMonth + ", expirationYear=" + expirationYear + ", cardholderName="
				+ cardholderName + ", cardBalance=" + cardBalance + ", si=" + shippingAddress + "]";
	}

}
