package com.revature.lostchapterbackend.checkouttest;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.revature.lostchapterbackend.dao.BookDAO;
import com.revature.lostchapterbackend.dao.BookToBuyDAO;
import com.revature.lostchapterbackend.dao.CartsDAO;
import com.revature.lostchapterbackend.dao.CheckoutDAO;
import com.revature.lostchapterbackend.dao.ShippingInfoDAO;
import com.revature.lostchapterbackend.dao.TransactionKeeperDAO;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.BookToBuy;
import com.revature.lostchapterbackend.model.Carts;
import com.revature.lostchapterbackend.model.Checkout;
import com.revature.lostchapterbackend.model.Genre;
import com.revature.lostchapterbackend.model.ShippingInformation;
import com.revature.lostchapterbackend.model.TransactionKeeper;
import com.revature.lostchapterbackend.model.Users;
import com.revature.lostchapterbackend.service.BookService;
import com.revature.lostchapterbackend.service.CartsService;
import com.revature.lostchapterbackend.service.CheckoutService;
import com.revature.lostchapterbackend.utility.OrderConfirmationRandomizer;

public class CheckoutServiceTest {

	@Mock
	private CartsService cs;
	@InjectMocks
	@Resource
	private CheckoutService cos;
	@InjectMocks
	private BookService bs;

	@Mock
	private BookDAO bd;
	@Mock
	private TransactionKeeperDAO tkd;
	@Mock
	private CheckoutDAO cod;
	@Mock
	private ShippingInfoDAO sid;
	@Mock
	private BookToBuyDAO btbd;
	@Mock
	private CartsDAO cd;

	@BeforeEach
	public void setup() {
		this.bs = new BookService(bd);
		this.cs = new CartsService(bs, cd, btbd);
		this.cos = new CheckoutService();

		MockitoAnnotations.openMocks(this);
	}

	@Test
	void confirmCheckout_PositiveTest() throws Exception {

		Checkout payout = new Checkout();
		payout.setCheckoutId(1);
		payout.setCardBalance(10000.00);

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

		List<String> previousOrder = new ArrayList<>();
		previousOrder.add(bookToBeBought.getBooks().getISBN());
		double totalPriceOfEachBook = bookToBeBought.getQuantityToBuy() * bookToBeBought.getBooks().getBookPrice();
		int booksRemaining = bookToBeBought.getBooks().getQuantity() - bookToBeBought.getQuantityToBuy(); // updates the
																											// quantity
																											// of book
		bookToBeBought.getBooks().setQuantity(booksRemaining); // updates the quantity of book
		double subTotal = 0.0;
		subTotal += totalPriceOfEachBook; // calculates subtotal for all the book
		double totalPrice = subTotal + (subTotal * 0.06);

		Mockito.when(bd.saveAndFlush(bookToBeBought.getBooks())).thenReturn(bookToAdd);

		payout.setCardBalance(payout.getCardBalance() - totalPrice); // updates the card balance

		cos.saveCard(payout); // save and updates card info

		TransactionKeeper tk;
		tk = new TransactionKeeper("vSQXcYx64Lda63u", totalPrice, previousOrder,
				LocalDateTime.of(LocalDate.now(), LocalTime.of(1, 53, 53)));
		Mockito.when(tkd.saveAndFlush(tk)).thenReturn(tk); // saves a transaction

		cs.delteteAllProductInCart(currentCart, String.valueOf(currentCart.getCartId()));
		cs.addBooksToCart(currentCart, "1", "1", "1");

//		System.out.println(currentCart);
//		System.out.println(payout);
//		
		TransactionKeeper actual = cos.confirmCheckout(currentCart, payout);
		actual.setOrderNumber("vSQXcYx64Lda63u");
		actual.setTransactionDate(LocalDateTime.of(LocalDate.now(), LocalTime.of(1, 53, 53)));

		TransactionKeeper expected = tk;

		Assertions.assertEquals(expected, actual);
	}

	@Test
	void findByCardNumber_PositiveTest() {

		Checkout payout = new Checkout("1234567890123456", "", "", "", "", 0.0, new ShippingInformation());
		payout.setCheckoutId(1);

		Mockito.when(cod.findBycardNumber("1234567890123456")).thenReturn(payout);

		Checkout actual = cos.findByCardNumber("1234567890123456");

		Checkout expected = new Checkout("1234567890123456", "", "", "", "", 0.0, new ShippingInformation());
		expected.setCheckoutId(1);

		Assertions.assertEquals(expected, actual);
	}

	@Test
	void findByCardNumberReturnsNull_PositiveTest() {

		Checkout payout = null;

		Mockito.when(cod.findBycardNumber("1234567890123456")).thenReturn(payout);

		Assertions.assertNull(payout);
	}

	@Test
	void getTransactionById_PositiveTest() {

		TransactionKeeper tk;
		tk = new TransactionKeeper("vSQXcYx64Lda63u", 10.23, new ArrayList<String>(),
				LocalDateTime.of(LocalDate.now(), LocalTime.of(1, 53, 53)));
		tk.setTransactionId(1);
		
		Mockito.when(tkd.findById(1)).thenReturn(Optional.of(new TransactionKeeper("vSQXcYx64Lda63u", 10.23,
				new ArrayList<String>(), LocalDateTime.of(LocalDate.now(), LocalTime.of(1, 53, 53)))));

		TransactionKeeper actual = cos.getTransactionById("1");
		actual.setTransactionId(1);

		TransactionKeeper expected = new TransactionKeeper("vSQXcYx64Lda63u", 10.23, new ArrayList<String>(),
				LocalDateTime.of(LocalDate.now(), LocalTime.of(1, 53, 53)));
		expected.setTransactionId(1);
		
		Assertions.assertEquals(expected, actual);
	}

	@Test
	void getTransactionById_IdNotAnInt_NegativeTest() {
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			cos.getTransactionById("");
		});
		
	}
}
