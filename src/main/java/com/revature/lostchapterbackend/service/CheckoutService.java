package com.revature.lostchapterbackend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.dao.BookDAO;
import com.revature.lostchapterbackend.dao.CheckoutDAO;
import com.revature.lostchapterbackend.dao.ShippingInfoDAO;
import com.revature.lostchapterbackend.dao.TransactionKeeperDAO;
import com.revature.lostchapterbackend.model.BookToBuy;
import com.revature.lostchapterbackend.model.Carts;
import com.revature.lostchapterbackend.model.Checkout;
import com.revature.lostchapterbackend.model.TransactionKeeper;
import com.revature.lostchapterbackend.utility.OrderConfirmationRandomizer;

@Service
public class CheckoutService {

	double totalPriceOfEachBook = 0.0;
	double totalPrice = 0.0;
	double subTotal = 0.0;
	int booksRemaining = 0;
	final double TAX = 0.06; // 6%

	@Autowired
	private BookDAO bd;

	@Autowired
	private CartsService cs;

	@Autowired
	private TransactionKeeperDAO tkd;

	@Autowired
	private CheckoutDAO cod;
	
	@Autowired 
	private ShippingInfoDAO sid;
	
	private TransactionKeeper tk;

	public TransactionKeeper confirmCheckout(Carts currentCart, Checkout payout) throws Exception {
		
		// gets all Books from the current cart
		List<BookToBuy> getTotalAmountOfAllBooks = currentCart.getBooksToBuy();
		// checks if cart is empty;
		if (getTotalAmountOfAllBooks.isEmpty()) {
			throw new Exception ("No Books currently");
		}	
		// A list to save all previous order
		List<String> previousOrder = new ArrayList<>();
		
		// Iterate each BookToBuy object from the List of BookToBuy
		for (BookToBuy b : getTotalAmountOfAllBooks) {		
			previousOrder.add(b.getBooks().getISBN()); // Add every ISBN number to the previous order
			if (b.getBooks().isSaleIsActive()) { // check if it's on sale
				this.totalPriceOfEachBook = (b.getBooks().getBookPrice()
						- (b.getBooks().getBookPrice() * b.getBooks().getSaleDiscountRate())) * b.getQuantityToBuy();
			} else { // if not
				this.totalPriceOfEachBook = b.getQuantityToBuy() * b.getBooks().getBookPrice();
			}
			
			this.booksRemaining = b.getBooks().getQuantity() - b.getQuantityToBuy(); // updates the quantity of book
			b.getBooks().setQuantity(booksRemaining); // updates the quantity of book
			this.subTotal += this.totalPriceOfEachBook; // calculates subtotal for all the book
			this.totalPrice = this.subTotal + (this.subTotal * this.TAX); // calculates total price including tax

			bd.saveAndFlush(b.getBooks()); // every iteration, save and update the necessary info
		}

		payout.setCardBalance(payout.getCardBalance() - this.totalPrice); // updates the card balance

		OrderConfirmationRandomizer ocr = new OrderConfirmationRandomizer(); // creates a random order number
		
		this.saveCard(payout); // save and updates card info

		tk = new TransactionKeeper(ocr.randomBankAccount(), this.totalPrice, previousOrder, LocalDateTime.now());
		tkd.saveAndFlush(tk); // saves a transaction

		

		cs.delteteAllProductInCart(currentCart, String.valueOf(currentCart.getCartId())); // clear outs all the books in the cart

		return tk;

	}

	public void saveCard(Checkout payout) {

		sid.save(payout.getShippingAddress());
		cod.saveAndFlush(payout);

	}

	public Checkout findByCardNumber(String cardNumber) {

		if (cod.findBycardNumber(cardNumber) == null) {
			return null;
		} else {
			return cod.findBycardNumber(cardNumber);
		}

	}
}
