package com.revature.lostchapterbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.lostchapterbackend.model.Checkout;

public interface CheckoutDAO extends JpaRepository<Checkout, Integer>{

}
