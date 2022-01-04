package com.revature.lostchapterbackend.service;

import java.security.InvalidParameterException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.dao.BookDAO;
import com.revature.lostchapterbackend.model.Book;

@Service
public class BookService {

	@Autowired
	private BookDAO bd;

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
		}catch (NumberFormatException e) {
			throw new InvalidParameterException("The genreId entered must be an int.");
		}
		
		
	}

	public List<Book> getBooksByKeyword(String keyword) {
		
		return null;
	}

}
