package com.revature.lostchapterbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.lostchapterbackend.model.Carts;

public interface CartsDAO extends JpaRepository<Carts, Integer>{

}
