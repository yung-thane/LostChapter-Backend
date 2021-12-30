package com.revature.lostchapterbackend.service;

import java.util.Iterator;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.dao.CartsDAO;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.BookToBuy;
import com.revature.lostchapterbackend.model.Carts;
import com.revature.lostchapterbackend.model.Genre;

@Service
public class CartsService {

	 @Autowired
	 private BookService bs;

	@Autowired
	private CartsDAO cd;

	public Carts getCartById(String id)throws NoResultException {

		int cartId = Integer.parseInt(id);

		return cd.selectACartById(cartId);
	}

	public Carts addBooksToCart(Carts currentCart, String userId, String bookId, String quantityToBuy) {

		currentCart = this.getCartById(userId); // checking if carts exist
		
		Book b = bs.getBookById(bookId);
		
		int amountToBuy = Integer.parseInt(quantityToBuy);
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

		return cd.insertToCart(currentCart, booksToBeBought);
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

	public Carts delteteProductInCart(Carts currentCart, String cartId, String productId) {
		currentCart = this.getCartById(cartId);
		int prodId = Integer.parseInt(productId);

		List<BookToBuy> currentBooksInTheList = currentCart.getBooksToBuy();
		int quantityToDelete = 0;

		Iterator<BookToBuy> iter = currentBooksInTheList.iterator();
		BookToBuy b1 = null;
		while (iter.hasNext()) {
			b1 = iter.next();
			if (b1.getBooks().getBookId() == prodId) {
				iter.remove();
				quantityToDelete = b1.getId();
				break;
			} else {
				//throw new BookNotFoundException("Product not found on this cart");
			}
		}
		currentCart.setBooksToBuy(currentBooksInTheList);

		return  cd.deleteAProductInTheCart(currentCart, quantityToDelete);
	}

}
