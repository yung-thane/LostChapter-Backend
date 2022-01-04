package com.revature.lostchapterbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.lostchapterbackend.model.Genre;

public interface GenreDAO extends JpaRepository<Genre, Integer> {

}
