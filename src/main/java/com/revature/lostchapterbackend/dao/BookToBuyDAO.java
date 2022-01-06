package com.revature.lostchapterbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.lostchapterbackend.model.BookToBuy;

public interface BookToBuyDAO extends JpaRepository<BookToBuy, Integer>{

}
