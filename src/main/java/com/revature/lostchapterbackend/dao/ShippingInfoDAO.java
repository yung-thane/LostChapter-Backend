package com.revature.lostchapterbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.lostchapterbackend.model.ShippingInformation;

public interface ShippingInfoDAO extends JpaRepository<ShippingInformation, Integer> {

}
