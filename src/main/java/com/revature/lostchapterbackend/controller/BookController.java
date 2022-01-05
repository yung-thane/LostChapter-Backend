package com.revature.lostchapterbackend.controller;

import java.security.InvalidParameterException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.lostchapterbackend.dto.AddBookDTO;
import com.revature.lostchapterbackend.exceptions.BookNotFoundException;
import com.revature.lostchapterbackend.exceptions.GenreNotFoundException;
import com.revature.lostchapterbackend.exceptions.ISBNAlreadyExists;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.service.BookService;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class BookController {

	@Autowired
	private BookService bs;

	@GetMapping(path = "/books")
	public List<Book> getAllBooks() {
		List<Book> bookList = bs.getAllBooks();

		return bookList;

	}

	@GetMapping(path = "/books/{id}")
	public ResponseEntity<Object> getBookById(@PathVariable(value = "id") String id) {
		try {
			Book book = bs.getBookById(id);
			return ResponseEntity.status(200).body(book);
		} catch (BookNotFoundException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}

	}

	@GetMapping(path = "/books/genre/{genreId}")
	public List<Book> getBookByGenreId(@PathVariable(value = "genreId") String genreId) {

		return bs.getBooksByGenreId(genreId);

	}

	@GetMapping(path = "/books/search/{keyword}")
	public List<Book> getBookByKeyword(@PathVariable(value = "keyword") String keyword) {

		return bs.getBooksByKeyword(keyword);

	}

	@GetMapping(path = "/books/sales")
	public List<Book> getBookBySale() {

		return bs.getBooksBySale();

	}

	@PostMapping(path = "/books")
	public ResponseEntity<Object> addNewBook(@RequestBody AddBookDTO dto) {
		try {
			Book addedBook = bs.addBook(dto);
			return ResponseEntity.status(201).body(addedBook);
		} catch (InvalidParameterException e) {
			return ResponseEntity.status(400).body(e.getMessage());

		} catch (GenreNotFoundException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (ISBNAlreadyExists e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}

	}

}
