package com.revature.lostchapterbackend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
<<<<<<< HEAD
=======
//import org.springframework.data.repository.query.Param;
>>>>>>> main

import com.revature.lostchapterbackend.model.Book;

public interface BookDAO extends JpaRepository<Book, Integer>{

<<<<<<< HEAD
	@Query("SELECT b FROM Book b WHERE b.genre.id = :genreId")
	public List<Book> getByGenreId(int genreId);

=======
	

	@Query("SELECT b FROM Book b WHERE b.genre.id = :genreId")
	public List<Book> getByGenreId(int genreId);


	public List<Book> findBybookNameIgnoreCaseContaining(String keyword);


	public List<Book> findBysaleIsActiveTrue();
	
	

>>>>>>> main
}
