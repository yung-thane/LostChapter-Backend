package com.revature.lostchapterbackend.usertests;

import java.security.InvalidParameterException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.revature.lostchapterbackend.dao.BookDAO;
import com.revature.lostchapterbackend.dao.GenreDAO;
import com.revature.lostchapterbackend.dto.AddOrUpdateBookDTO;
import com.revature.lostchapterbackend.exceptions.GenreNotFoundException;
import com.revature.lostchapterbackend.exceptions.ISBNAlreadyExists;
import com.revature.lostchapterbackend.exceptions.SaleDiscountRateException;
import com.revature.lostchapterbackend.exceptions.SynopsisInputException;
import com.revature.lostchapterbackend.model.Genre;
import com.revature.lostchapterbackend.utility.ValidateBookUtil;

public class ValidateBookUtilUnitTests {

	@Mock
	GenreDAO mockGenreDao;
	
	@Mock
	BookDAO mockBookDao;

	@InjectMocks
	ValidateBookUtil sut; 
	
	private String reallyBigString = StringUtils.repeat("a", 1000);
			
	
	@BeforeEach
	public void Setup() {
		
		this.sut = new ValidateBookUtil();
		MockitoAnnotations.initMocks(this);
		
		
	}
	
	@Test
	public void test_ValidateBookInput_positive() throws InvalidParameterException, GenreNotFoundException, ISBNAlreadyExists, SynopsisInputException, SaleDiscountRateException {
		
		AddOrUpdateBookDTO bookDTO = new AddOrUpdateBookDTO("test", "test", "test", "test", 1, 1, 1996, "test", "test", false, 0.5, 20.99, "test" );
		
		Optional<Genre> mockGenre = Optional.of(new Genre());
		
		Mockito.when(mockGenreDao.findById(1)).thenReturn(mockGenre);
		
		sut.validateBookInput(bookDTO); //Doesnt return anything if good. Only throws exceptions if there is an issue
		
	}
	
	@Test
	public void test_ValidateBookInput_all_blank_or_null_negative() {
		
		AddOrUpdateBookDTO bookDTO = new AddOrUpdateBookDTO(); 
		
		InvalidParameterException e = Assertions.assertThrows(InvalidParameterException.class, ()-> {
			
			sut.validateBookInput(bookDTO);
			
		});
		
		Assertions.assertEquals(e.getMessage(), "ISBN, book name, synopsis, author, genre, edition, publisher, image cannot be blank.");
		
	}
	
	@Test
	public void test_ValidateBookInput_bookName_blank_negative() {
		
		AddOrUpdateBookDTO bookDTO = new AddOrUpdateBookDTO("test", "", "test", "test", 1, 1, 1996, "test", "test", false, 0.5, 20.99, "test" );
		
		InvalidParameterException e = Assertions.assertThrows(InvalidParameterException.class, ()-> {
			
			sut.validateBookInput(bookDTO);
			
		});
		
		Assertions.assertEquals(e.getMessage(), "Book name cannot be blank.");
		
	}
	
	@Test
	public void test_ValidateBookInput_synopsis_blank_negative() {
		
		AddOrUpdateBookDTO bookDTO = new AddOrUpdateBookDTO("test", "test", "", "test", 1, 1, 1996, "test", "test", false, 0.5, 20.99, "test" );
	
		InvalidParameterException e = Assertions.assertThrows(InvalidParameterException.class, ()-> {
			
			sut.validateBookInput(bookDTO);
			
		});
		
		Assertions.assertEquals(e.getMessage(), "Synopsis cannot be blank.");
		
	}
	
	@Test
	public void test_ValidateBookInput_author_blank_negative() {
		
		AddOrUpdateBookDTO bookDTO = new AddOrUpdateBookDTO("test", "test", "test", "", 1, 1, 1996, "test", "test", false, 0.5, 20.99, "test" );
		
		InvalidParameterException e = Assertions.assertThrows(InvalidParameterException.class, ()-> {
			
			sut.validateBookInput(bookDTO);
			
		});
		
		Assertions.assertEquals(e.getMessage(), "Author cannot be blank.");
		
	}
	
	@Test
	public void test_ValidateBookInput_genre_zero_negative() {
		
		AddOrUpdateBookDTO bookDTO = new AddOrUpdateBookDTO("test", "test", "test", "test", 0, 1, 1996, "test", "test", false, 0.5, 20.99, "test" );
		
		InvalidParameterException e = Assertions.assertThrows(InvalidParameterException.class, ()-> {
			
			sut.validateBookInput(bookDTO);
			
		});
		
		Assertions.assertEquals(e.getMessage(), "Genre cannot be blank.");
		
	}
	
	@Test
	public void test_ValidateBookInput_edition_blank_negative() {
		
		AddOrUpdateBookDTO bookDTO = new AddOrUpdateBookDTO("test", "test", "test", "test", 1, 1, 1996, "", "test", false, 0.5, 20.99, "test" );
	
		InvalidParameterException e = Assertions.assertThrows(InvalidParameterException.class, ()-> {
			
			sut.validateBookInput(bookDTO);
			
		});
		
		Assertions.assertEquals(e.getMessage(), "Edition cannot be blank.");
		
	}
	
	@Test
	public void test_ValidateBookInput_publisher_blank_negative() {
		
		AddOrUpdateBookDTO bookDTO = new AddOrUpdateBookDTO("test", "test", "test", "test", 1, 1, 1996, "test", "", false, 0.5, 20.99, "test" );
		
		InvalidParameterException e = Assertions.assertThrows(InvalidParameterException.class, ()-> {
			
			sut.validateBookInput(bookDTO);
			
		});
		
		Assertions.assertEquals(e.getMessage(), "Publisher cannot be blank.");
		
	}
	
	@Test
	public void test_ValidateBookInput_bookImage_blank_negative() {
		
		AddOrUpdateBookDTO bookDTO = new AddOrUpdateBookDTO("test", "test", "test", "test", 1, 1, 1996, "test", "test", false, 0.5, 20.99, "" );
		
		InvalidParameterException e = Assertions.assertThrows(InvalidParameterException.class, ()-> {
			
			sut.validateBookInput(bookDTO);
			
		});
		
		Assertions.assertEquals(e.getMessage(), "Image cannot be blank.");
		
	}
	
	@Test
	public void test_ValidateBookInput_quantity_year_price_zero_negative() {
		
		AddOrUpdateBookDTO bookDTO = new AddOrUpdateBookDTO("test", "test", "test", "test", 1, 0, 0, "test", "test", false, 0.5, 0, "test" );
		
		InvalidParameterException e = Assertions.assertThrows(InvalidParameterException.class, ()-> {
			
			sut.validateBookInput(bookDTO);
			
		});
		
		Assertions.assertEquals(e.getMessage(), "Quantity, year, book price cannot be less than or equal to 0.");
		
	}
	
	@Test
	public void test_ValidateBookInput_year_zero_negative() {
		
		AddOrUpdateBookDTO bookDTO = new AddOrUpdateBookDTO("test", "test", "test", "test", 1, 1, 0, "test", "test", false, 0.5, 20.99, "test" );
		
		InvalidParameterException e = Assertions.assertThrows(InvalidParameterException.class, ()-> {
			
			sut.validateBookInput(bookDTO);
			
		});
		
		Assertions.assertEquals(e.getMessage(), "Year cannot be less than or equal to 0.");
		
	}
	
	@Test
	public void test_ValidateBookInput_price_zero_negative() {
		
		AddOrUpdateBookDTO bookDTO = new AddOrUpdateBookDTO("test", "test", "test", "test", 1, 1, 1996, "test", "test", false, 0.5, 0, "test" );
		
		InvalidParameterException e = Assertions.assertThrows(InvalidParameterException.class, ()-> {
			
			sut.validateBookInput(bookDTO);
			
		});
		
		Assertions.assertEquals(e.getMessage(), "Book price cannot be less than or equal to 0.");
		
	}
	
	@Test
	public void test_ValidateBookInput_synopsis_greater_than_800_negative() {
		
		AddOrUpdateBookDTO bookDTO = new AddOrUpdateBookDTO("test", "test", reallyBigString, "test", 1, 1, 1996, "test", "test", false, 0.5, 20.99, "test" );
		
		SynopsisInputException e = Assertions.assertThrows(SynopsisInputException.class, () -> {
			
			sut.validateBookInput(bookDTO);
			
		});
		
		Assertions.assertEquals(e.getMessage(), "Synopsis cannot be longer than 800 characters.");
		
	}
	
	@Test
	public void test_ValidateBookInput_discount_zero_negative() {
		
		AddOrUpdateBookDTO bookDTO = new AddOrUpdateBookDTO("test", "test", "test", "test", 1, 1, 1996, "test", "test", false, 0, 20.99, "test" );
		
		SaleDiscountRateException e = Assertions.assertThrows(SaleDiscountRateException.class, () -> {
			
			sut.validateBookInput(bookDTO);
			
		});
		
		Assertions.assertEquals(e.getMessage(), "Sale discount rate must be more than 0.0 and less than or equal to 0.90");
		
	}
	
	@Test
	public void test_ValidateBookInput_discount_greater_than_1_negative() {
		
		AddOrUpdateBookDTO bookDTO = new AddOrUpdateBookDTO("test", "test", "test", "test", 1, 1, 1996, "test", "test", false, 1, 20.99, "test" );
		
		SaleDiscountRateException e = Assertions.assertThrows(SaleDiscountRateException.class, () -> {
			
			sut.validateBookInput(bookDTO);
			
		});
		
		Assertions.assertEquals(e.getMessage(), "Sale discount rate must be more than 0.0 and less than or equal to 0.90");
		
	}
	
	@Test
	public void test_ValidateBookInput_genre_not_found_exception_negative() {
		
		AddOrUpdateBookDTO bookDTO = new AddOrUpdateBookDTO("test", "test", "test", "test", 1, 1, 1996, "test", "test", false, 0.5, 20.99, "test" );
	
		
		GenreNotFoundException e = Assertions.assertThrows(GenreNotFoundException.class, () -> {
			
			sut.validateBookInput(bookDTO);
			
		});
		
		Assertions.assertEquals(e.getMessage(), "Genre doesn't exist");
		
	}
	
	//Below here, I will write tests to break the code. Everything above is for code coverage.
	
}
