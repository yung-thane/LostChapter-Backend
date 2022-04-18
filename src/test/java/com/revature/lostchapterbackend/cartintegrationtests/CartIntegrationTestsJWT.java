package com.revature.lostchapterbackend.cartintegrationtests;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

import com.revature.lostchapterbackend.dto.SignUpDto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lostchapterbackend.dto.LoginDto;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.BookToBuy;
import com.revature.lostchapterbackend.model.Carts;
import com.revature.lostchapterbackend.model.Genre;
import com.revature.lostchapterbackend.model.Users;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class CartIntegrationTestsJWT {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private EntityManagerFactory emf;

	@Autowired
	private ObjectMapper mapper;

	private Users expectedUser;
	private BookToBuy positiveBookToBuy;
	private BookToBuy negativeBookToBuy;

	private static String testToken;
	private SignUpDto testDto = new SignUpDto(
			"test",
			"password",
			"testFirstName",
			"testLastName",
			21,
			"test@aol.com",
			"09/08/1990",
			"addresswest",
			"Customer");

	@BeforeEach
	public void setup() {



		Book positiveBook = new Book();	//Id should be 1
		Book noStockBook = new Book();	//Id should be 2
		Genre genre = new Genre();	//Id should be 1

		genre.setGenre("test");


		positiveBook.setISBN("test");
		positiveBook.setBookName("test");
		positiveBook.setSynopsis("test");
		positiveBook.setAuthor("test");
		positiveBook.setGenre(genre);
		positiveBook.setQuantity(100);
		positiveBook.setYear(2000);
		positiveBook.setEdition("test");
		positiveBook.setPublisher("test");
		positiveBook.setSaleIsActive(false);
		positiveBook.setSaleDiscountRate(0);
		positiveBook.setBookPrice(0);
		positiveBook.setBookImage("test");


		noStockBook.setISBN("test");
		noStockBook.setBookName("test");
		noStockBook.setSynopsis("test");
		noStockBook.setAuthor("test");
		noStockBook.setGenre(genre);
		noStockBook.setQuantity(0);
		noStockBook.setYear(2000);
		noStockBook.setEdition("test");
		noStockBook.setPublisher("test");
		noStockBook.setSaleIsActive(false);
		noStockBook.setSaleDiscountRate(0);
		noStockBook.setBookPrice(0);
		noStockBook.setBookImage("test");

//		Users user = new Users();	//Id should be 1
//		user.setUsername("test");
//		user.setPassword("5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8"); //its "password"
//		user.setFirstName("test");
//		user.setLastName("test");
//		user.setAge(25);
//		user.setEmail("test");
//		user.setBirthday("test");
//		user.setAddress("test");
//		user.setRole("Customer");




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
		this.expectedUser.setRole("Customer");

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

	}

	@Test
	public void cart_test_removing_item_from_cart_positive() throws Exception {



	}


	@Test
	public void cart_test_attempting_to_add_to_cart_item_out_of_stock_negative() throws Exception {

	}

	@Test
	public void cart_test_adding_item_to_cart_when_item_already_in_cart_positive() throws Exception {

	}

	@Test
	public void cart_test_adding_item_of_multiple_quantity_positive() throws Exception {


	}

	@Test
	public void cart_test_adding_item_of_negative_quantity_negative() throws Exception {

	}

	@Test
	public void cart_test_get_cart_by_id_positive() throws Exception {


	}

	@Test
	public void cart_test_get_cart_no_matching_id_negative() throws Exception {


	}

	@Test
	public void cart_test_delete_book_in_cart_positive() throws Exception {


	}

	@Test
	public void cart_test_delete_book_not_in_cart_negative() throws Exception {


	}

	@Test
	public void cart_test_delete_all_items_in_cart_positive() throws Exception {

	}

	@Test
	public void cart_test_add_book_to_cart_user_id_doesnt_match_pattern_negative() throws Exception {

	}

	@Test
	public void cart_test_add_book_to_cart_bookId_doesnt_match_pattern_negative() throws Exception {
}

	@Test
	public void cart_test_add_book_to_cart_quantityToBuy_doesnt_match_pattern_negative() throws Exception {


	}

	@Test
	public void cart_test_trying_to_add_book_that_doesnt_exist_negative() throws Exception {

	}


	@Test
	public void cart_test_trying_to_access_cart_that_doesnt_exist_negative() throws Exception {


	}

	@Test
	public void cart_test_trying_to_get_cart_cart_id_not_int_negative() throws Exception {


	}

	@Test
	public void cart_test_delete_item_in_cart_cartId_doesnt_match_pattern_negative() throws Exception {


	}

	@Test
	public void cart_test_delete_item_in_cart_bookId_doesnt_match_pattern_negative() throws Exception {


	}

	@Test
	public void cart_test_delete_item_in_cart_bookId_doesnt_exist_negative() throws Exception {

	}

	@Test
	public void cart_test_delete_item_in_cart_cartId_doesnt_exist_negative() throws Exception {


	}

}
