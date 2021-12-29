package com.revature.lostchapterbackend.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.dao.UserDao;
import com.revature.lostchapterbackend.dto.SignUpDto;
import com.revature.lostchapterbackend.exceptions.InvalidLoginException;
import com.revature.lostchapterbackend.exceptions.InvalidParameter;
import com.revature.lostchapterbackend.exceptions.UserNotFoundException;
import com.revature.lostchapterbackend.model.Users;
import com.revature.lostchapterbackend.utility.HashUtil;

@Service
public class UserService {
	
	@Autowired
	private UserDao ud;
	
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public Users createUser(SignUpDto dto) throws InvalidLoginException, InvalidParameter, NoSuchAlgorithmException {
		
		if (dto.getUsername().trim().equals("") || dto.getPassword().trim().equals("")
                || dto.getFirstName().trim().equals("") || dto.getLastName().trim().equals("")
                || dto.getBirthday().trim().equals("") || dto.getEmail().trim().equals("")
                || dto.getAddress().trim().equals("")  || dto.getRole().trim().equals("")) {
            throw new InvalidParameter("Do not leave any information blank");
        }
		
		if (dto.getAge() < 5 || dto.getAge() > 125) {
			throw new InvalidParameter("Age cannot be less than 5 or greater than 125");
		}
		
		String algorithm = "SHA-256";
		String hashedPassword = HashUtil.hashPassword(dto.getPassword().trim(), algorithm);
		dto.setPassword(hashedPassword);
		System.out.println(hashedPassword);
		
		try {
			
			Users createdUser = this.ud.addUser(dto);
			return createdUser;
		} catch (DataAccessException e) {
			throw new InvalidLoginException("Username already exists");
		}
	}
	
	public Users getUser(String access, String password) throws InvalidLoginException, NoSuchAlgorithmException {
		
		logger.info("UserService.getUser() invoked");
		
		Users user = ud.getUser(access, password);
		System.out.println(user.getPassword());
		
		try {
			
			if (user != null) {
				String algorithm = "SHA-256";
				String hashedInputPassword = HashUtil.hashInputPassword(password.trim(), algorithm);
				System.out.println(hashedInputPassword);
				
				logger.debug("hashedInputPassword {}", hashedInputPassword);
				logger.debug("user.getPassword {}", user.getPassword());
				
				Boolean correctPassword = hashedInputPassword.equals(user.getPassword());
				
				if (correctPassword) {
					return user;
				}
				else {
					throw new InvalidLoginException("Username and/or password is incorrect");
				}
			}
			else {
				throw new InvalidLoginException("Username and/or password is incorrect");
			}
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

	public Users getUserByEmail(String email) {
		logger.info("UserService.getUserByEmail() invoked");
		
		Users users = this.ud.getUserByEmail(email);
		
		return users;
	}

}
