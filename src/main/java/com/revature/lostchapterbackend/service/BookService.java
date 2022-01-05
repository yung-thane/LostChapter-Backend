package com.revature.lostchapterbackend.service;

import java.security.InvalidParameterException;
import java.util.List;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.dao.BookDAO;

import com.revature.lostchapterbackend.model.Book;

import com.revature.lostchapterbackend.dao.GenreDAO;
import com.revature.lostchapterbackend.dto.AddBookDTO;
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

	public Book getBookById(String id) {

		try {
			int bookId = Integer.parseInt(id);
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

//	public List<Book> getBooksByKeyword(String keyword) {
//
//		return null;
//	}


	public List<Book> getBooksByKeyword(String keyword) {

		return bd.findBybookNameIgnoreCaseContaining(keyword);
	}

	public List<Book> getBooksBySale() {

		return bd.findBysaleIsActiveTrue();
	}

	public Book addBook(AddBookDTO dto) {
		
//		System.out.println(dto);

		boolean blankInputs = false;
		StringBuilder blankInputStrings = new StringBuilder();

		if (StringUtils.isBlank(dto.getISBN())) {
			blankInputStrings.append("ISBN ");
			blankInputs = true;
		}

		if (StringUtils.isBlank(dto.getBookName().trim())) {
			blankInputStrings.append("bookName ");
			blankInputs = true;
		}

		if (StringUtils.isBlank(dto.getSynopsis().trim())) {
			blankInputStrings.append("synopsis ");
			blankInputs = true;
		}

		if (StringUtils.isBlank(dto.getAuthor().trim())) {
			blankInputStrings.append("author");
			blankInputs = true;
		}
		String genre = Integer.toString(dto.getGenre());
		
		if (StringUtils.isBlank(genre)) {
			blankInputStrings.append("genre ");
			blankInputs = true;

		}
		String quantity = Integer.toString(dto.getQuantity());
		if (StringUtils.isBlank(quantity)) {
			blankInputStrings.append("quantity ");
			blankInputs = true;
		}

		String year = Integer.toString(dto.getYear());
		if (StringUtils.isBlank(year)) {
			blankInputStrings.append("year ");
			blankInputs = true;
		}

		if (StringUtils.isBlank(dto.getEdition().trim())) {
			blankInputStrings.append("edition ");
			blankInputs = true;
		}

		if (StringUtils.isBlank(dto.getPublisher().trim())) {
			blankInputStrings.append("publisher ");
			blankInputs = true;

		}

		if (StringUtils.isBlank(dto.getBindingType().trim())) {
			blankInputStrings.append("bindingType ");
			blankInputs = true;

		}

		String sale = Boolean.toString(dto.isSaleIsActive());
		if (StringUtils.isBlank(sale)) {
			blankInputStrings.append("isSaleActive ");
			blankInputs = true;

		}

		if (StringUtils.isBlank(dto.getCondition().trim())) {
			blankInputStrings.append("condition ");
			blankInputs = true;
		}

		String price = Double.toString(dto.getBookPrice());
		if (StringUtils.isBlank(price)) {
			blankInputStrings.append("price ");
			blankInputs = true;
		}

		if (StringUtils.isBlank(dto.getBookImage().trim())) {
			blankInputStrings.append("price ");
			blankInputs = true;
		}

		if (blankInputs) {
			blankInputStrings.append("cannot be blank.");
			throw new InvalidParameterException(blankInputStrings.toString());
		}
	
		try {
			Genre getGenre = gd.findById(dto.getGenre()).get();
			
			Book addedBook = new Book(dto.getISBN(), dto.getBookName(), dto.getSynopsis(), dto.getAuthor(), getGenre, dto.getQuantity(), dto.getYear(),
					dto.getEdition(), dto.getPublisher(), dto.getBindingType(), dto.isSaleIsActive(), dto.getSaleDiscountRate(),
					dto.getCondition(), dto.getBookPrice(), dto.getBookImage());
			return bd.saveAndFlush(addedBook);
		}catch (Exception e){
			
			
		}
		
		return null;

	}

}
