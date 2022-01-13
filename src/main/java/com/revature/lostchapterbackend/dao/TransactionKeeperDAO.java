package com.revature.lostchapterbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.lostchapterbackend.model.TransactionKeeper;

public interface TransactionKeeperDAO extends JpaRepository<TransactionKeeper, Integer>{

}
