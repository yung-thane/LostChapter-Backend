package com.revature.lostchapterbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.lostchapterbackend.model.Checkout;

public interface CheckoutDAO extends JpaRepository<Checkout, Integer>{

	@Query("SELECT c FROM Checkout c WHERE c.cardNumber = :cardNumber")
	Checkout findBycardNumber(String cardNumber);

}
