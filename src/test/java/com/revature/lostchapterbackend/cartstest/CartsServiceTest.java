package com.revature.lostchapterbackend.cartstest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.revature.lostchapterbackend.dao.BookDAO;
import com.revature.lostchapterbackend.dao.BookToBuyDAO;
import com.revature.lostchapterbackend.dao.CartsDAO;
import com.revature.lostchapterbackend.exceptions.BookNotFoundException;
import com.revature.lostchapterbackend.exceptions.OutOfStockException;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.BookToBuy;
import com.revature.lostchapterbackend.model.Carts;
import com.revature.lostchapterbackend.model.Genre;
import com.revature.lostchapterbackend.model.Users;
import com.revature.lostchapterbackend.service.BookService;
import com.revature.lostchapterbackend.service.CartsService;

public class CartsServiceTest {

	private CartsService cs;
	private CartsDAO cd;
	private BookToBuyDAO btbd;

	private BookService bs;
	private BookDAO bd;

	@BeforeEach
	public void setUp() {
		this.bd = mock(BookDAO.class);
		this.bs = new BookService(bd);
		this.cd = mock(CartsDAO.class);
		this.btbd = mock(BookToBuyDAO.class);
		this.cs = new CartsService(bs, cd, btbd);
	}

	@Test // Happy Path
	void addBooksToCart_PostiveTest() throws OutOfStockException, BookNotFoundException {

		List<BookToBuy> bookToBuy = new ArrayList<>();

		Carts currentCart = new Carts(new Users());
		currentCart = new Carts(bookToBuy);
		currentCart.setCartId(1);

		Mockito.when(cd.findById(currentCart.getCartId())).thenReturn(Optional.of(currentCart));

		Genre g = new Genre(1, "Novel"); // Manually Insertting Genre
		Book bookToAdd = new Book("9783462015393", "The Catcher In The Rye",
				"set around the 1950s and is narrated by a young man named Holden Caulfield.", "J.D. Salinger", g, 100,
				1951, "1st edition", "Little, Brown", false, 0.0, 8.07, "imageURL");
		bookToAdd.setBookId(1);

		BookToBuy bookToBeBought = new BookToBuy(bookToAdd, 1);
		bookToBeBought.setId(1);

		Mockito.when(bd.findById(bookToAdd.getBookId())).thenReturn(Optional.of(bookToAdd));

		bookToBuy = currentCart.getBooksToBuy();
		bookToBuy.add(bookToBeBought);
		currentCart.setBooksToBuy(bookToBuy);

		Mockito.when(btbd.saveAndFlush(bookToBeBought)).thenReturn(bookToBeBought);
		Mockito.when(cd.saveAndFlush(currentCart)).thenReturn(currentCart);

		Carts actual = cs.addBooksToCart(currentCart, "1", "1", "1");

		Carts expected = currentCart;

		Assertions.assertEquals(expected, actual);

	}

	@Test // Sad Path
	void addBooksToCart_NoSuchElementException_NegativeTest() {

		Assertions.assertThrows(NoSuchElementException.class, () -> {
			cs.addBooksToCart(null, "1", "1", "1");
		});
	}

	@Test // Sad Path
	void addBooksToCart_OutOfStockException_NegativeTest() {

		Carts currentCart = new Carts();
		currentCart.setCartId(1);

		Mockito.when(cd.findById(currentCart.getCartId())).thenReturn(Optional.of(currentCart));

		Genre g = new Genre(1, "Novel");
		Book bookToAdd = new Book("9783462015393", "The Catcher In The Rye",
				"set around the 1950s and is narrated by a young man named Holden Caulfield.", "J.D. Salinger", g, 0,
				1951, "1st edition", "Little, Brown", false, 0.00, 8.07, "imageURL");
		bookToAdd.setBookId(1);

		Mockito.when(bd.findById(bookToAdd.getBookId())).thenReturn(Optional.of(bookToAdd));

		Assertions.assertThrows(OutOfStockException.class, () -> {
			cs.addBooksToCart(currentCart, "1", "1", "1");
		});
	}

	@Test // Sad Path
	void addMoreBooksToCart_BookNotFoundException_NegativeTest() { // It is working, but Exception handling needs to
																	// be implemented in the BookController
		List<BookToBuy> bookToBuy = new ArrayList<>();
		Carts currentCart = new Carts(bookToBuy);
		currentCart.setCartId(1);
		Mockito.when(cd.findById(currentCart.getCartId())).thenReturn(Optional.of(currentCart));

		Assertions.assertThrows(BookNotFoundException.class, () -> {
			cs.addBooksToCart(currentCart, "1", "1", "1");
		});
	}

	@Test // Happy Path
	void deleteBookInTheCart_PositiveTest() throws BookNotFoundException {

		List<BookToBuy> bookToBuy = new ArrayList<>();

		Carts currentCart = new Carts(new Users());
		currentCart = new Carts(bookToBuy);
		currentCart.setCartId(1);

		Mockito.when(cd.findById(currentCart.getCartId())).thenReturn(Optional.of(currentCart));

		Genre g = new Genre(1, "Novel");
		Book bookToAdd = new Book("9783462015393", "The Catcher In The Rye",
				"set around the 1950s and is narrated by a young man named Holden Caulfield.", "J.D. Salinger", g, 100,
				1951, "1st edition", "Little, Brown", false, 0.0, 8.07, "imageURL");
		bookToAdd.setBookId(2);
		BookToBuy bookToBeBought = new BookToBuy(bookToAdd, 1);
		bookToBeBought.setId(1);
		bookToBuy.add(bookToBeBought);

		Mockito.when(bd.findById(bookToAdd.getBookId())).thenReturn(Optional.of(bookToAdd));

		List<BookToBuy> currentBooksInTheCart = currentCart.getBooksToBuy();
		int bookToDelete = 0;

		Iterator<BookToBuy> iter = currentBooksInTheCart.iterator();
		BookToBuy q1 = null;
		while (iter.hasNext()) {
			q1 = iter.next();
			iter.remove();
			bookToDelete = q1.getId();
		}

		currentCart.setBooksToBuy(currentBooksInTheCart);

		btbd.deleteById(bookToDelete);

		Mockito.verify(btbd, times(1)).deleteById(1);

		Mockito.when(cd.saveAndFlush(currentCart)).thenReturn(currentCart);

		Carts actual = cs.delteteProductInCart(currentCart, "1", "2");

		Carts expected = currentCart;

		Assertions.assertEquals(expected, actual);
	}

	@Test // Happy Path
	void deleteAllBookInTheCart_PositiveTest() throws BookNotFoundException {

		List<BookToBuy> bookToBuy = new ArrayList<>();

		Carts currentCart = new Carts(new Users());
		currentCart = new Carts(bookToBuy);
		currentCart.setCartId(1);

		Mockito.when(cd.findById(currentCart.getCartId())).thenReturn(Optional.of(currentCart));

		Genre g1 = new Genre(1, "Novel");
		Book book1 = new Book("9783462015393", "The Catcher In The Rye",
				"set around the 1950s and is narrated by a young man named Holden Caulfield.", "J.D. Salinger", g1, 100,
				1951, "1st edition", "Little, Brown", false, 0.0, 8.07, "imageURL");
		book1.setBookId(1);
		Genre g2 = new Genre(2, "Fantasy");
		Book book2 = new Book("9783462015393", "The Fantasy Book",
				"set around the 1950s and is narrated by a young man named Holden Caulfield.", "J.D. Salinger", g2, 100,
				1951, "1st edition", "Little, Brown", false, 0.0, 8.07, "imageURL");
		book2.setBookId(2);
		BookToBuy bookToBeBought = new BookToBuy(book1, 1);
		bookToBeBought.setId(1);
		BookToBuy bookToBeBought2 = new BookToBuy(book2, 1);
		bookToBeBought.setId(2);
		bookToBuy.add(bookToBeBought);
		bookToBuy.add(bookToBeBought2);

		List<BookToBuy> currentBooksInTheCart = currentCart.getBooksToBuy();

		Iterator<BookToBuy> iter = currentBooksInTheCart.iterator();
		BookToBuy q1 = null;
		while (iter.hasNext()) {
			q1 = iter.next();
			iter.remove();
			currentCart.setBooksToBuy(currentBooksInTheCart);
		}

		btbd.deleteAll();

		Mockito.verify(btbd, times(1)).deleteAll();

		Mockito.when(cd.saveAndFlush(currentCart)).thenReturn(currentCart);

		Carts actual = cs.delteteAllProductInCart(currentCart, "1");

		Carts expected = currentCart;

		Assertions.assertEquals(expected, actual);
	}

}
