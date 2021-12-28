package com.revature.lostchapterbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.dao.UserDao;
import com.revature.lostchapterbackend.dto.SignUpDto;
import com.revature.lostchapterbackend.exceptions.InvalidLoginException;
import com.revature.lostchapterbackend.exceptions.InvalidParameter;
import com.revature.lostchapterbackend.exceptions.UserNotFoundException;
import com.revature.lostchapterbackend.model.Users;

@Service
public class UserService {
	
	@Autowired
	private UserDao ud;
	
	public Users createUser(SignUpDto dto) throws InvalidLoginException {
		
		try {
			Users createdUser = this.ud.addUser(dto);
			return createdUser;
		} catch (DataAccessException e) {
			throw new InvalidLoginException("Username already exists");
		}
	}
	
	public Users getUser(String access, String password) throws InvalidLoginException {
		
		try {
			Users user = ud.getUser(access, password);
			return user;
		} catch(DataAccessException e) {
			throw new InvalidLoginException("Username and/or password is incorrect");
		}
	}
	
	public void deleteUserById(Users currentUser) throws UserNotFoundException {
		
		if (currentUser != null) {
			int currentUserId = currentUser.getId();
			ud.deleteUserById(currentUserId);
		}
		else {
			throw new UserNotFoundException("Current user is null");
		}
	}

}
