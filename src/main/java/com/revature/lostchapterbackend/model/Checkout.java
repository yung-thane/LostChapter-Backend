package com.revature.lostchapterbackend.model;

import java.util.Objects;

public class Checkout {

	private int checkoutId;
	private String cardNumber; // 13 - 19 restriction
	private String securityCode; // 3 - 4 restriction
	private String expirationMonth;
	private String expirationYear;
	private String cardholderName;
	// private Users user; // @OneToMany ? // can be accessed by currentlyLoggedInUser

	public Checkout() {
		super();
	}

	public Checkout(String cardNumber, String securityCode, String expirationMonth, String expirationYear,
			String cardholderName/* , Users user */) {
		super();
		this.cardNumber = cardNumber;
		this.securityCode = securityCode;
		this.expirationMonth = expirationMonth;
		this.expirationYear = expirationYear;
		this.cardholderName = cardholderName;
//		this.user = user;
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

//	public Users getUser() {
//		return user;
//	}
//
//	public void setUser(Users user) {
//		this.user = user;
//	}

	@Override
	public int hashCode() {
		return Objects.hash(cardNumber, cardholderName, checkoutId, expirationMonth, expirationYear,
				securityCode/*
							 * , user
							 */);
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
		return Objects.equals(cardNumber, other.cardNumber) && Objects.equals(cardholderName, other.cardholderName)
				&& checkoutId == other.checkoutId && expirationMonth == other.expirationMonth
				&& expirationYear == other.expirationYear && Objects.equals(securityCode, other.securityCode)
		/* && Objects.equals(user, other.user) */;
	}

	@Override
	public String toString() {
		return "Checkout [checkoutId=" + checkoutId + ", cardNumber=" + cardNumber + ", securityCode=" + securityCode
				+ ", expirationMonth=" + expirationMonth + ", expirationYear=" + expirationYear + ", cardholderName="
				+ cardholderName + "]";
	}

}
