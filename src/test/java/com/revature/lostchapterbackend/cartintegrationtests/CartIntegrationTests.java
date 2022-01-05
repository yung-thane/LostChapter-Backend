package com.revature.lostchapterbackend.cartintegrationtests;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.BookToBuy;
import com.revature.lostchapterbackend.model.Carts;
import com.revature.lostchapterbackend.model.Genre;
import com.revature.lostchapterbackend.model.Users;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class CartIntegrationTests {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired 
	private EntityManagerFactory emf;
	
	@Autowired
	private ObjectMapper mapper; 
	
	private Users expectedUser; 
	private BookToBuy positiveBookToBuy; 
	private BookToBuy negativeBookToBuy; 
	
	@BeforeEach
	public void setup() {
		
		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(Session.class);
		Transaction tx =session.beginTransaction();
		
		Book positiveBook = new Book();	//Id should be 1
		Book noStockBook = new Book();	//Id should be 2
		Genre genre = new Genre();	//Id should be 1
		
		genre.setGenre("test");
		
		session.persist(genre);		
			
		
		
		positiveBook.setISBN("test");
		positiveBook.setBookName("test");
		positiveBook.setSynopsis("test");
		positiveBook.setAuthor("test");
		positiveBook.setGenre(genre);
		positiveBook.setQuantity(100);
		positiveBook.setYear(2000);
		positiveBook.setEdition("test");
		positiveBook.setPublisher("test");
		positiveBook.setBindingType("test");
		positiveBook.setSaleIsActive(false);
		positiveBook.setSaleDiscountRate(0);
		positiveBook.setCondition("test");
		positiveBook.setBookPrice(0);
		positiveBook.setBookImage("test");
		
		session.persist(positiveBook);
		
		noStockBook.setISBN("test");
		noStockBook.setBookName("test");
		noStockBook.setSynopsis("test");
		noStockBook.setAuthor("test");
		noStockBook.setGenre(genre);
		noStockBook.setQuantity(0);
		noStockBook.setYear(2000);
		noStockBook.setEdition("test");
		noStockBook.setPublisher("test");
		noStockBook.setBindingType("test");
		noStockBook.setSaleIsActive(false);
		noStockBook.setSaleDiscountRate(0);
		noStockBook.setCondition("test");
		noStockBook.setBookPrice(0);
		noStockBook.setBookImage("test");
		
		session.persist(noStockBook);
		
		Users user = new Users();	//Id should be 1
		user.setUsername("test");
		user.setPassword("5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8"); //its "password"
		user.setFirstName("test");
		user.setLastName("test");
		user.setAge(25);
		user.setEmail("test");
		user.setBirthday("test");
		user.setAddress("test");
		user.setRole("test");
		
		session.persist(user);
		
		Carts cart = new Carts(user);
		
		session.persist(cart);
		
		
		tx.commit();
		
		session.close();
		
		this.expectedUser = new Users();
		
		this.expectedUser.setId(1);
		this.expectedUser.setUsername("test");
		this.expectedUser.setPassword("5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8"); //its "password"
		this.expectedUser.setFirstName("test");
		this.expectedUser.setLastName("test");
		this.expectedUser.setAge(25);
		this.expectedUser.setEmail("test");
		this.expectedUser.setBirthday("test");
		this.expectedUser.setAddress("test");
		this.expectedUser.setRole("test");
		
		this.positiveBookToBuy = new BookToBuy();
		this.negativeBookToBuy = new BookToBuy();
		
		this.positiveBookToBuy.setBooks(positiveBook);
		this.positiveBookToBuy.setId(1);
		this.positiveBookToBuy.setQuantityToBuy(1);
		
		this.negativeBookToBuy.setBooks(noStockBook);
		this.negativeBookToBuy.setId(2);
		this.negativeBookToBuy.setQuantityToBuy(1);
		
		
		
	}

	@Test
	public void cart_test_adding_item_to_cart_positive() throws Exception {
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/users/1/cart").param("bookId", "1").param("quantityToBuy", "1");
		Carts expectedCart = new Carts();

		expectedCart.setUser(this.expectedUser);
		expectedCart.setCartId(1);
		
		ArrayList<BookToBuy> bookToBuyList = new ArrayList<>();
		bookToBuyList.add(positiveBookToBuy);
		
		expectedCart.setBooksToBuy(bookToBuyList);
		
		String expectedJson = mapper.writeValueAsString(expectedCart);
		
		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(200)).andExpect(MockMvcResultMatchers.content().json(expectedJson));

		
		
		
	}
	
	@Test
	public void cart_test_removing_item_from_cart_positive() throws Exception {
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/users/1/cart").param("bookId", "1").param("quantityToBuy", "1");
		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(200));
		
		Carts expectedCart = new Carts();
		expectedCart.setUser(this.expectedUser);
		expectedCart.setCartId(1);
		ArrayList<BookToBuy> bookToBuyList = new ArrayList<>();
		expectedCart.setBooksToBuy(bookToBuyList);
		
		builder = MockMvcRequestBuilders.delete("/users/1/cart").param("bookId", "1");
		
		String expectedJson = mapper.writeValueAsString(expectedCart);
		
		System.out.println(expectedJson);
		
		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(200)).andExpect(MockMvcResultMatchers.content().json(expectedJson));
		
		
	}
	
	
	@Test
	public void cart_test_attempting_to_add_to_cart_item_out_of_stock_negative() {
		
		Assertions.fail("Implement me");
		
	}
	
	@Test
	public void cart_test_adding_item_to_cart_when_item_already_in_cart_positive() {
		
		Assertions.fail("Implement me");
		
	}
	
	@Test
	public void cart_test_adding_item_of_multiple_quantity_positive() {
		
		Assertions.fail("Implement me");
		
	}
	
	@Test
	public void cart_test_adding_item_of_negative_quantity_negative() {
		
		Assertions.fail("Implement me");
		
	}
	
	@Test
	public void cart_test_get_all_items_in_cart_positive() {
		
		Assertions.fail("Implement me");
		
	}
	
	@Test
	public void cart_test_delete_book_in_cart_positive() {
		
		Assertions.fail("Implement me");
		
	}
	
	@Test
	public void cart_test_delete_book_not_in_cart_negative() {
		
		Assertions.fail("Implement me");
		
	}
	
}
