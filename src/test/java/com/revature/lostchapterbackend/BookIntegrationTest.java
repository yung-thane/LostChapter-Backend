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

	@BeforeEach
	public void setUp() {

		EntityManager em = emf.createEntityManager();
		Session session = em.unwrap(Session.class);
		Transaction tx = session.beginTransaction();

		// Not sure how to deal with genre
		Book testBook = new Book("iSBN", "bookName", "synopsis", "author", nonfiction, 1, 1996, "edition", "publisher",
				"bindingType", false, 0.99, "condition", 10.99, "bookImage"); //book id 1
		session.persist(testBook);
		Book testBook2 = new Book("iSBN2", "bookName2", "synopsis", "author", nonfiction, 1, 1996, "edition", "publisher",
				"bindingType", false, 0.99, "condition", 10.99, "bookImage"); //book id 1
		session.persist(testBook2);
		Book testBook3 = new Book("iSBN3", "bookName3", "synopsis", "author", nonfiction, 1, 1996, "edition", "publisher",
				"bindingType", false, 0.99, "condition", 10.99, "bookImage"); //book id 1
		session.persist(testBook3);
		

		tx.commit();
		session.close();
	}

	@Test //positive test
	public void testGetAllBook() {

		// Arrange


		// Act and Assert
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/books");

		//fix genre here also
		Book expectedBook = new Book("iSBN", "bookName", "synopsis", "author", nonfiction, 1, 1996, "edition",
				"publisher", "bindingType", false, 0.99, "condition", 10.99, "bookImage");
		expectedBook.setBookId(1);
		Book expectedBook2 = new Book("iSBN2", "bookName2", "synopsis", "author", nonfiction, 1, 1996, "edition", "publisher",
				"bindingType", false, 0.99, "condition", 10.99, "bookImage"); //book id 1
		expectedBook2.setBookId(2);
		Book expectedBook3 = new Book("iSBN3", "bookName3", "synopsis", "author", nonfiction, 1, 1996, "edition", "publisher",
				"bindingType", false, 0.99, "condition", 10.99, "bookImage"); //book id 1
		expectedBook3.setBookId(3);

		String expectedJson = mapper.writeValueAsString(expectedBook);

		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(expectedJson));
		

	}
	
	@Test //positive test
	public void testGetBookById() {
		
		//Arrange
		
		
		//Act
		
		// Not sure how best to send id of 1
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/books/{id}");
		
		//fix genre here also
		Book expectedBook = new Book("iSBN", "bookName", "synopsis", "author", nonfiction, 1, 1996, "edition",
				"publisher", "bindingType", false, 0.99, "condition", 10.99, "bookImage");
		expectedBook.setBookId(1);
		
		String expectedJson = mapper.writeValueAsString(expectedBook);

		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(expectedJson));
		
	}
	
	@Test //positive test
	public void testGetAllBooksByGenere() {
		
		//Arrange
		
		//Act 
		// Not sure how best to genre id of 1
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/books/genre/{genreId}");
		
		Book expectedBook = new Book("iSBN", "bookName", "synopsis", "author", nonfiction, 1, 1996, "edition",
				"publisher", "bindingType", false, 0.99, "condition", 10.99, "bookImage");
		//Genre genre
		expectedBook.setGenre(Genre nonfiction);
		
		String expectedJson = mapper.writeValueAsString(expectedBook);

		this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().json(expectedJson));
	}
	
	@Test //positive test
	public void testGetBooksByKeyWord() {
		
		//Arrange
		
		//Act
		// Not sure how best to send id of 1
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/books/search/{keyword}");
		
		//fix genre here also
		Book expectedBook = new Book("iSBN", "bookName", "synopsis", "author", nonfiction, 1, 1996, "edition",
				"publisher", "bindingType", false, 0.99, "condition", 10.99, "bookImage");
		expectedBook.setBookId(1);
		
	}
}
