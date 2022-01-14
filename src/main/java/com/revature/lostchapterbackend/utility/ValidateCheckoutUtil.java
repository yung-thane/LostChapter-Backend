package com.revature.lostchapterbackend.utility;

import java.util.HashSet;
import java.util.Set;

import com.revature.lostchapterbackend.model.Checkout;

public class ValidateCheckoutUtil {

	public void verifyCheckout(Checkout payout) throws Exception {

		final String CARD_NUMBER = payout.getCardNumber().trim().replaceAll("\\s+","");
		int month = Integer.parseInt(payout.getExpirationYear());
		if (CARD_NUMBER.length() >= 20 || CARD_NUMBER.length() <= 12) {
			throw new Exception("Card number can't be less than or equal to 12 or greater than or equal to 20");
		}
		if (CARD_NUMBER.equals("")) {
			throw new Exception("Card number cannot be empty!");
		}
		Set<String> validMonth = new HashSet<>();
		validMonth.add("01");
		validMonth.add("02");
		validMonth.add("03");
		validMonth.add("04");
		validMonth.add("05");
		validMonth.add("06");	
		validMonth.add("07");
		validMonth.add("08");
		validMonth.add("09");
		validMonth.add("10");
		validMonth.add("11");
		validMonth.add("12");		
		if (payout.getExpirationMonth().trim().length() >= 3 || payout.getExpirationMonth().trim().length() <= 1) {
			throw new Exception("valid expiration month is from 01 to 12");
		}
		if (!validMonth.contains(payout.getExpirationMonth())) {
			throw new Exception("valid expiration month is from 01 to 12");
		}
		if (payout.getExpirationMonth().trim().equals("")) {
			throw new Exception("expiration month cannot be empty");
		}
		if (payout.getExpirationYear().trim().length() >= 3 || payout.getExpirationYear().trim().length() <= 1) {
			throw new Exception("valid expiration year starts from 22 (2022)");
		}
		if (month < 22) {
			throw new Exception("valid expiration year starts from 22 (2022)");
		}
		if (payout.getSecurityCode().trim().length() >= 5 || payout.getSecurityCode().trim().length() <= 2) {
			throw new Exception("security code must have 3 or 4 values");
		}
		if (payout.getSecurityCode().trim().equals("")) {
			throw new Exception("CCV cannot be empty");
		}
		if (payout.getCardholderName().trim().equals("")) {
			throw new Exception("Card Holder Name cannot be empty");
		}
		if (payout.getCardholderName().length() >= 255 || payout.getCardholderName().length() <= 0) {
			throw new Exception("Card Holder Name cannot be greater than 255 or less than 0");
		}
		
		Set<String> validDeliveryDate = new HashSet<>();
		// HashSet<String> valid deliveryDate 
		// 1. 7 Days Delivery Date
		// 2. 1 Month Delivery Date
		validDeliveryDate.add("7 Days Delivery Date");
		validDeliveryDate.add("1 Month Delivery Date");
		
		if (!validDeliveryDate.contains(payout.getShippingAddress().getDeliveryDate())) {
			throw new Exception("Only options are 7 Days or 1 Month Delivery Date");
		}
	}
	
	public void verifyCardInfo(Checkout cardInfo, Checkout payout) throws Exception {
		
		if (!cardInfo.getExpirationMonth().equals(payout.getExpirationMonth())) {
			throw new Exception ("expiration month doesn't match the card number information");
		}else if (!cardInfo.getExpirationYear().equals(payout.getExpirationYear())) {
			throw new Exception ("expiration year doesn't match the card number information");
		}else if (!cardInfo.getSecurityCode().equals(payout.getSecurityCode())) {
			throw new Exception ("CVV doesn't match the card number information");
		}
		
	}

}
