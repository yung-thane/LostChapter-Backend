package com.revature.lostchapterbackend.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.lostchapterbackend.annotation.Customer;
import com.revature.lostchapterbackend.model.Carts;
import com.revature.lostchapterbackend.model.Checkout;
import com.revature.lostchapterbackend.model.TransactionKeeper;
import com.revature.lostchapterbackend.model.Users;
import com.revature.lostchapterbackend.service.CartsService;
import com.revature.lostchapterbackend.service.CheckoutService;
import com.revature.lostchapterbackend.utility.ValidateCheckoutUtil;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class CheckoutController {

	@Autowired
	private HttpServletRequest req;

	@Autowired
	private ValidateCheckoutUtil validateCheckoutUtil;

	@Autowired
	private CheckoutService cs;

	@Autowired
	private CartsService css;
	
	private TransactionKeeper tk;

	@Customer
	@PostMapping(path = "/user/checkout")
	public ResponseEntity<Object> payout(@RequestBody Checkout payout) {

		// main way to get cartId
		Users currentlyLoggedInUser = (Users) req.getSession().getAttribute("currentUser");
		
		try {
			Carts c = css.getCartById(String.valueOf(currentlyLoggedInUser.getId()));
			validateCheckoutUtil.verifyCheckout(payout); // validates card information
			
			// removes all the spaces in the  card number when user inputs spaces
			payout.setCardNumber(payout.getCardNumber().trim().replaceAll("\\s+", "")); 
			
			// checks if card already exists
			Checkout getCardNumber = cs.findByCardNumber(payout.getCardNumber());
			//if it doesn't ...
			if (getCardNumber == null) {
				payout.setCardBalance(10000);
				tk = cs.confirmCheckout(c, payout);
				return ResponseEntity.status(200).body(tk);
			} else {
			// if it exists...
				tk = cs.confirmCheckout(c, getCardNumber);
				return ResponseEntity.status(200).body(tk);
			}
			
		} catch (Exception e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}

	}

}
