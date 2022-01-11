package com.revature.lostchapterbackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.dao.BookDAO;
import com.revature.lostchapterbackend.dao.CheckoutDAO;
import com.revature.lostchapterbackend.dao.TransactionKeeperDAO;
import com.revature.lostchapterbackend.model.Book;
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

	public void confirmCheckout(Carts c, Checkout payout) {
		// First get the total of the product
		String cartId = String.valueOf(c.getCartId());

		List<BookToBuy> getTotalAmountOfAllBooks = c.getBooksToBuy();
		List<Book> booksToBeKeeped = new ArrayList<>();

		for (BookToBuy b : getTotalAmountOfAllBooks) {
			booksToBeKeeped.add(b.getBooks());
			if (b.getBooks().isSaleIsActive()) {
				this.totalPriceOfEachBook = (b.getBooks().getBookPrice()
						- (b.getBooks().getBookPrice() * b.getBooks().getSaleDiscountRate())) * b.getQuantityToBuy();
			} else {
				this.totalPriceOfEachBook = b.getQuantityToBuy() * b.getBooks().getBookPrice();
			}
			this.booksRemaining = b.getBooks().getQuantity() - b.getQuantityToBuy();
			b.getBooks().setQuantity(booksRemaining);
			// System.out.println("Quantity remaining for book "+b.getBooks().getBookId() +
			// " is: " +b.getBooks().getQuantity());
			this.subTotal += this.totalPriceOfEachBook;
			// System.out.println(this.subTotal);
			this.totalPrice = this.subTotal + (this.subTotal * this.TAX);
			
			bd.saveAndFlush(b.getBooks());
		}

		payout.setCardBalance(payout.getCardBalance() - this.totalPrice);
		System.out.println(payout.getCardBalance());

		// save totalPrice in a order confirmation page model/transaction model
		System.out.println("total price with tax" + this.totalPrice);

		// Order Confirmation Page = total price, randomized string for order number
		// Add a like a transaction table to save previous order
		OrderConfirmationRandomizer ocr = new OrderConfirmationRandomizer();

		TransactionKeeper tk = new TransactionKeeper();
		tk = new TransactionKeeper(ocr.randomBankAccount(), this.totalPrice, booksToBeKeeped);
		tkd.saveAndFlush(tk);

		Carts updatedCart = cs.delteteAllProductInCart(c, cartId);
		System.out.println(updatedCart);

	}

	public void saveCard(Checkout payout) {

		cod.saveAndFlush(payout);

	}
}
