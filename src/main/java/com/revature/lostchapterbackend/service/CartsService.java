package com.revature.lostchapterbackend.service;

import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.dao.BookToBuyDAO;
import com.revature.lostchapterbackend.dao.CartsDAO;
import com.revature.lostchapterbackend.exceptions.BookNotFoundException;
import com.revature.lostchapterbackend.exceptions.OutOfStockException;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.BookToBuy;
import com.revature.lostchapterbackend.model.Carts;

@Service
public class CartsService {

	@Autowired
	private BookService bs;

	@Autowired
	private CartsDAO cd; // using JPA Repository

	@Autowired
	private BookToBuyDAO btbd;

	public CartsService(BookService bs, CartsDAO cd, BookToBuyDAO btbd) {
		// For mocking
		// For Unit Testing of Carts Service
		this.bs = bs;
		this.cd = cd;
		this.btbd = btbd;
	}

	public Carts getCartById(String id) {

		try {
			int cartId = Integer.parseInt(id);
			return cd.findById(cartId).get();

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("The Id entered must be an int.");

		}
	}

	public Carts addBooksToCart(Carts currentCart, String userId, String bookId, String quantityToBuy)
			throws OutOfStockException, BookNotFoundException {

		currentCart = this.getCartById(userId); // checking if carts exist

		Book b = bs.getBookById(bookId);

		if (b.getQuantity() <= 0) {
			throw new OutOfStockException("Currently Out of Stock...");
		}
		int amountToBuy = Integer.parseInt(quantityToBuy);

		if (amountToBuy <= 0) {
			throw new InvalidParameterException("Quantity to Buy cannot be less than or equal to zero!");
		}
		BookToBuy booksToBeBought = new BookToBuy(b, amountToBuy);

		List<BookToBuy> currentBooksInTheCart = currentCart.getBooksToBuy();

		boolean checkBook = checkBookInTheCart(currentBooksInTheCart, b);

		if (checkBook == false) {
			currentBooksInTheCart.add(booksToBeBought);
			currentCart.setBooksToBuy(currentBooksInTheCart);
		} else if (checkBook == true) {
			for (BookToBuy b1 : currentBooksInTheCart) {
				if (b1.getBooks() == b) {
					b1.setQuantityToBuy(b1.getQuantityToBuy() + amountToBuy);
					booksToBeBought = b1;
				}
			}
		}
		btbd.saveAndFlush(booksToBeBought);
		return cd.saveAndFlush(currentCart);

	}

	private boolean checkBookInTheCart(List<BookToBuy> currentBooksInTheCart, Book b) {
		boolean result = false;
		for (BookToBuy b1 : currentBooksInTheCart) {
			if (b1.getBooks() == b) {
				result = true;
			}
		}
		return result;
	}

	public Carts delteteProductInCart(Carts currentCart, String cartId, String productId) throws BookNotFoundException {
		currentCart = this.getCartById(cartId);
		int prodId = Integer.parseInt(productId);

		List<BookToBuy> currentBooksInTheList = currentCart.getBooksToBuy();
		int quantityToDelete = 0;
		try {
			Iterator<BookToBuy> iter = currentBooksInTheList.iterator();
			System.out.println(currentBooksInTheList);
			BookToBuy b1 = null;
			while (iter.hasNext()) {
				b1 = iter.next();
				if (b1.getBooks().getBookId() == prodId) {
					iter.remove();
					quantityToDelete = b1.getId();
				}
			}
			currentCart.setBooksToBuy(currentBooksInTheList);

			btbd.deleteById(quantityToDelete);
		} catch (EmptyResultDataAccessException e) {
			throw new BookNotFoundException("Product not found on this cart");
		}

		return cd.saveAndFlush(currentCart);
	}

	public Carts delteteAllProductInCart(Carts currentCart, String cartId) {
		currentCart = this.getCartById(cartId);

		List<BookToBuy> currentBooksInTheList = currentCart.getBooksToBuy();

		Iterator<BookToBuy> iter = currentBooksInTheList.iterator();
		System.out.println(currentBooksInTheList);
		BookToBuy b1 = null;
		while (iter.hasNext()) {
			b1 = iter.next();
			iter.remove();
			currentCart.setBooksToBuy(currentBooksInTheList);
			System.out.println(b1.getId());
			btbd.deleteById(b1.getId());
		}


		return cd.saveAndFlush(currentCart);
	}

}
