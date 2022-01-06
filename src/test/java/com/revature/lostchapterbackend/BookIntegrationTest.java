package com.revature.lostchapterbackend;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lostchapterbackend.dto.AddBookDTO;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.Genre;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookIntegrationTest {

	@Autowired
	private MockMvc mvc; // send fake http requests to the server

	@Autowired
	private EntityManagerFactory emf;

	@Autowired
	private ObjectMapper mapper; // translate JSON objects

	public Genre g;
	public Genre g2;

	@BeforeEach
	public void setUp() {
		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();

		g = new Genre();
		g.setGenre("Fiction");
		em.persist(g);
		g2 = new Genre();
		g2.setGenre("NonFiction");
		em.persist(g2);

		// Arrange
		Book actualBook = new Book("1234567879", "bookName", "synopsis",
				"author", g, 1, 1996, "edition",
				"publisher", "bindingType", true,
				0.99, "condition", 10.99, "");
		em.persist(actualBook);
		Book actualBook2 = new Book("2122232425", "bookName2", "synopsis",
				"author", g, 1, 1996, "edition",
				"publisher", "bindingType", true,
				0.99, "condition", 10.99, "");
		em.persist(actualBook2);
		Book actualBook3 = new Book("91011121314", "bookName3", "synopsis",
				"author", g2, 1, 1996, "edition", "publisher",
				"bindingType", false, 0.99, "condition",
				10.99, ""); //book id 1
		em.persist(actualBook3);

		tx.commit();
		session.close();

	}

	@Test
	public void testGetAllBook_positive() throws Exception {


		// Act and Assert
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/books");

		//fix genre here also
		Book expectedBook = new Book("1234567879", "bookName", "synopsis",
				"author", g, 1, 1996, "edition",
				"publisher", "bindingType", true,
				0.99, "condition", 10.99, "");
		expectedBook.setBookId(1);
		Book expectedBook2 = new Book("2122232425", "bookName2", "synopsis",
				"author", g, 1, 1996, "edition",
				"publisher", "bindingType", true,
				0.99, "condition", 10.99, ""); //book id 1
		expectedBook2.setBookId(2);
		Book expectedBook3 = new Book("91011121314", "bookName3", "synopsis",
				"author", g2, 1, 1996, "edition", "publisher",
				"bindingType", false, 0.99, "condition",
				10.99, ""); //book id 1
		expectedBook3.setBookId(3);

		List<Book> expectedBooks = new ArrayList<>();
		expectedBooks.add(expectedBook);
		expectedBooks.add(expectedBook2);
		expectedBooks.add(expectedBook3);

		String expectedJson = mapper.writeValueAsString(expectedBooks);

		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(expectedJson));

	}
	
	@Test
	public void testGetBookById_positive() throws Exception {

		// Not sure how best to send id of 1
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/books/1");

		//fix genre here also
		Book expectedBook = new Book("1234567879", "bookName", "synopsis",
				"author", g, 1, 1996, "edition",
				"publisher", "bindingType", true,
				0.99, "condition", 10.99, "");
		expectedBook.setBookId(1);

		String expectedJson = mapper.writeValueAsString(expectedBook);

		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(expectedJson));

	}

	@Test
	public void testGetAllBooksByGenre_positive() throws Exception {

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/books/genre/1");

		Book expectedBook1 = new Book("1234567879", "bookName", "synopsis",
				"author", g, 1, 1996, "edition",
				"publisher", "bindingType", true,
				0.99, "condition", 10.99, "");
		expectedBook1.setBookId(1);
		Book expectedBook2 = new Book("2122232425", "bookName2", "synopsis",
				"author", g, 1, 1996, "edition",
				"publisher", "bindingType", true,
				0.99, "condition", 10.99, "");
		expectedBook2.setBookId(2);

		List<Book> expectedBooks = new ArrayList<>();
		expectedBooks.add(expectedBook1);
		expectedBooks.add(expectedBook2);

		String expectedJson = mapper.writeValueAsString(expectedBooks);

		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(expectedJson));
	}

	@Test
	public void testGetBooksByKeyWord_positive() throws Exception {

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/books/search/bookName");

		Book expectedBook1 = new Book("1234567879", "bookName", "synopsis",
				"author", g, 1, 1996, "edition",
				"publisher", "bindingType", true,
				0.99, "condition", 10.99, "");
		expectedBook1.setBookId(1);
		Book expectedBook2 = new Book("2122232425", "bookName2", "synopsis",
				"author", g, 1, 1996, "edition",
				"publisher", "bindingType", true,
				0.99, "condition", 10.99, "");
		expectedBook2.setBookId(2);
		Book expectedBook3 = new Book("91011121314", "bookName3", "synopsis",
				"author", g2, 1, 1996, "edition", "publisher",
				"bindingType", false, 0.99, "condition",
				10.99, "");
		expectedBook3.setBookId(3);

		List<Book> expectedBooks = new ArrayList<>();
		expectedBooks.add(expectedBook1);
		expectedBooks.add(expectedBook2);
		expectedBooks.add(expectedBook3);

		String expectedJson = mapper.writeValueAsString(expectedBooks);

		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(expectedJson));

	}

	@Test
	public void testGetBooksBySales_positive() throws Exception {

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/books/sales");

		Book expectedBook1 = new Book("1234567879", "bookName", "synopsis",
				"author", g, 1, 1996, "edition",
				"publisher", "bindingType", true,
				0.99, "condition", 10.99, "");
		expectedBook1.setBookId(1);
		Book expectedBook2 = new Book("2122232425", "bookName2", "synopsis",
				"author", g, 1, 1996, "edition",
				"publisher", "bindingType", true,
				0.99, "condition", 10.99, "");
		expectedBook2.setBookId(2);

		List<Book> expectedBooks = new ArrayList<>();
		expectedBooks.add(expectedBook1);
		expectedBooks.add(expectedBook2);

		String expectedJson = mapper.writeValueAsString(expectedBooks);

		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(expectedJson));

	}

	@Test
	public void testAddBook_positive() throws Exception {

		AddBookDTO actualBook = new AddBookDTO("2425262728", "bookName4", "synopsis",
				"author", 1, 1, 1996, "edition",
				"publisher", "bindingType", true,
				0.99, "condition", 10.99, "");
		String jsonToSend = mapper.writeValueAsString(actualBook);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/books").content(jsonToSend)
				.contentType(MediaType.APPLICATION_JSON);

		Book expectedBook1 = new Book("2425262728", "bookName4", "synopsis",
				"author", g, 1, 1996, "edition",
				"publisher", "bindingType", true,
				0.99, "condition", 10.99, "");
		expectedBook1.setBookId(4);


		String expectedJson = mapper.writeValueAsString(expectedBook1);

		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(201))
				.andExpect(MockMvcResultMatchers.content().json(expectedJson));

	}


}
