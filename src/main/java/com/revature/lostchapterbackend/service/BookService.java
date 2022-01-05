package com.revature.lostchapterbackend.service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.dao.BookDAO;
import com.revature.lostchapterbackend.dao.GenreDAO;
import com.revature.lostchapterbackend.dto.AddBookDTO;
import com.revature.lostchapterbackend.exceptions.BookNotFoundException;
import com.revature.lostchapterbackend.exceptions.GenreNotFoundException;
import com.revature.lostchapterbackend.exceptions.ISBNAlreadyExists;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.Genre;

@Service
public class BookService {

	@Autowired
	private BookDAO bd;

	@Autowired
	private GenreDAO gd;

	public List<Book> getAllBooks() {

		return bd.findAll();
	}

	public Book getBookById(String id) throws BookNotFoundException {
		try {
			int bookId = Integer.parseInt(id);
			if 	 (!bd.findById(bookId).isPresent()) {
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
	
	public Book addBook(AddBookDTO dto) throws GenreNotFoundException, ISBNAlreadyExists {

//		System.out.println(dto);

		boolean blankInputs = false;
		StringBuilder blankInputStrings = new StringBuilder();

		if (StringUtils.isBlank(dto.getISBN())) {
			blankInputStrings.append("ISBN");
			blankInputs = true;
		}

		if (StringUtils.isBlank(dto.getBookName())) {
			if (blankInputs) {
				blankInputStrings.append(", book name");
				blankInputs = true;
			} else {
				blankInputStrings.append("Book name");
				blankInputs = true;

			}

		}

		if (StringUtils.isBlank(dto.getSynopsis())) {
			if (blankInputs) {
				blankInputStrings.append(", synopsis");
				blankInputs = true;
			}else {
				blankInputStrings.append("Synopsis");
				blankInputs = true;

			}

		} 
		if (StringUtils.isBlank(dto.getAuthor())) {
			if (blankInputs) {
				blankInputStrings.append(", author");
				blankInputs = true;
			}else {
				blankInputStrings.append("Author");
				blankInputs = true;

			}

		} 
	//	String genre = Integer.toString();
		if (dto.getGenre() == 0) {
			if (blankInputs) {
				blankInputStrings.append(", genre");
				blankInputs = true;
			}else {
				blankInputStrings.append("Genre");
				blankInputs = true;

			}
			

		} 
		String quantity = Integer.toString(dto.getQuantity());
		if (StringUtils.isBlank(quantity)) {
			if (blankInputs) {
				blankInputStrings.append(", quantity");
				blankInputs = true;
			} else {
				blankInputStrings.append("Quantity");
				blankInputs = true;

			}

		}

		String year = Integer.toString(dto.getYear());
		if (StringUtils.isBlank(year)) {
			if (blankInputs) {
				blankInputStrings.append(", year");
				blankInputs = true;
			}else {
				blankInputStrings.append("Year");
				blankInputs = true;

			}

		} 
		if (StringUtils.isBlank(dto.getEdition())) {
			if (blankInputs) {
				blankInputStrings.append(", edition");
				blankInputs = true;
			}else {
				blankInputStrings.append("Edition");
				blankInputs = true;

			}

		} 
		if (StringUtils.isBlank(dto.getPublisher())) {
			if (blankInputs) {
				blankInputStrings.append(", publisher");
				blankInputs = true;
			}else {
				blankInputStrings.append("Publisher");
				blankInputs = true;

			}

		} 
		if (StringUtils.isBlank(dto.getBindingType())) {
			if (blankInputs) {
				blankInputStrings.append(", binding type");
				blankInputs = true;
			}else {
				blankInputStrings.append("Binding type");
				blankInputs = true;

			}

		} 

		String sale = Boolean.toString(dto.isSaleIsActive());
		if (StringUtils.isBlank(sale)) {
			if (blankInputs) {
				blankInputStrings.append(", sale");
				blankInputs = true;
			}else {
				blankInputStrings.append("Sale");
				blankInputs = true;

			}

		} 
		if (StringUtils.isBlank(dto.getCondition())) {
			if (blankInputs) {
				blankInputStrings.append(", condition");
				blankInputs = true;
			}else {
				blankInputStrings.append("Condition");
				blankInputs = true;
			}

		} 

		String price = Double.toString(dto.getBookPrice());
		if (StringUtils.isBlank(price)) {
			if (blankInputs) {
				blankInputStrings.append(", price");
				blankInputs = true;
			}else {
				blankInputStrings.append("Price");
				blankInputs = true;
			}

		} 
		if (StringUtils.isBlank(dto.getBookImage())) {
			if (blankInputs) {
				blankInputStrings.append(", image");
				blankInputs = true;
			}else {
				blankInputStrings.append("Image");
				blankInputs = true;
			}

		} 

		if (blankInputs) {
			blankInputStrings.append(" cannot be blank.");
			throw new InvalidParameterException(blankInputStrings.toString());
		}
		
		if (bd.findByISBN(dto.getISBN()).isPresent()) {
			throw new ISBNAlreadyExists("ISBN already used for another book");
		}
		

			
		if 	 (!gd.findById(dto.getGenre()).isPresent()) {
			throw new GenreNotFoundException("Genre doesn't exist");
		}
		
		Genre getGenre = gd.findById(dto.getGenre()).get();
		
			Book addedBook = new Book(dto.getISBN(), dto.getBookName(), dto.getSynopsis(), dto.getAuthor(), getGenre,
					dto.getQuantity(), dto.getYear(), dto.getEdition(), dto.getPublisher(), dto.getBindingType(),
					dto.isSaleIsActive(), dto.getSaleDiscountRate(), dto.getCondition(), dto.getBookPrice(),
					dto.getBookImage());
			
			
			return bd.saveAndFlush(addedBook);

		

	}

}
