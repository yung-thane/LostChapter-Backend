package com.revature.lostchapterbackend.service;

import java.security.InvalidParameterException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.dao.BookDAO;
import com.revature.lostchapterbackend.dao.GenreDAO;
import com.revature.lostchapterbackend.dto.AddOrUpdateBookDTO;
import com.revature.lostchapterbackend.exceptions.BookNotFoundException;
import com.revature.lostchapterbackend.exceptions.GenreNotFoundException;
import com.revature.lostchapterbackend.exceptions.ISBNAlreadyExists;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.Genre;
import com.revature.lostchapterbackend.utility.ValidateBookUtil;


@Service
public class BookService {

	@Autowired
	private BookDAO bd;
	
	@Autowired
	private ValidateBookUtil vBUtil;

	public BookService(BookDAO bd) {
		// For mocking
		// For Unit Testing
		this.bd = bd;
	}

	@Autowired
	private GenreDAO gd;

	public List<Book> getAllBooks() {

		return bd.findAll();
	}

	public Book getBookById(String id) throws BookNotFoundException {
		try {
			int bookId = Integer.parseInt(id);
			if (!bd.findById(bookId).isPresent()) {
				throw new BookNotFoundException("Book doesn't exist");
			}
			return bd.findById(bookId).get();
		} catch (NumberFormatException e) {
			throw new InvalidParameterException("The Id entered must be an int.");

		}

	}

	public List<Book> getBooksByGenreId(String genreId) {
		try {
			int gId = Integer.parseInt(genreId);
			return bd.getByGenreId(gId);
		} catch (NumberFormatException e) {
			throw new InvalidParameterException("The genreId entered must be an int.");
		}

	}

	public List<Book> getBooksByKeyword(String keyword) {

		return bd.findBybookNameIgnoreCaseContaining(keyword);
	}

	public List<Book> getBooksBySale() {

		return bd.findBysaleIsActiveTrue();
	}

	public Book addBook(AddOrUpdateBookDTO dto) throws GenreNotFoundException, ISBNAlreadyExists, InvalidParameterException {
		vBUtil.validateBookInput(dto);	
		Genre getGenre = gd.findById(dto.getGenre()).get();
		Book addedBook = new Book(dto.getISBN(), dto.getBookName(), dto.getSynopsis(), dto.getAuthor(), getGenre,
				dto.getQuantity(), dto.getYear(), dto.getEdition(), dto.getPublisher(), dto.isSaleIsActive(),
				dto.getSaleDiscountRate(), dto.getBookPrice(), dto.getBookImage());

		return bd.saveAndFlush(addedBook);

	}

	public Book updateBook(AddOrUpdateBookDTO dto, String id) throws BookNotFoundException {
		try {
			int bookId = Integer.parseInt(id);

			if (!bd.findById(bookId).isPresent()) {

				throw new BookNotFoundException("Book doesn't exist");
			}
			Book bookToUpdate = bd.findById(bookId).get();
		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Id must be in Int format");
		}
		
		
		return null;
	}

}
