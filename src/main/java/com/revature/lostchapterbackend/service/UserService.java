package com.revature.lostchapterbackend.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.utility.ValidateUtil;
import com.revature.lostchapterbackend.dao.UserDao;
import com.revature.lostchapterbackend.dto.SignUpDto;
import com.revature.lostchapterbackend.exceptions.InvalidLoginException;
import com.revature.lostchapterbackend.exceptions.InvalidParameterException;
import com.revature.lostchapterbackend.exceptions.UserNotFoundException;
import com.revature.lostchapterbackend.model.Users;
import com.revature.lostchapterbackend.utility.HashUtil;

@Service
public class UserService {
	
	@Autowired
	private UserDao ud;
	
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public Users createUser(SignUpDto dto) throws InvalidLoginException, InvalidParameterException, NoSuchAlgorithmException {
		
		if (dto.getUsername().trim().equals("") || dto.getPassword().trim().equals("")
                || dto.getFirstName().trim().equals("") || dto.getLastName().trim().equals("")
                || dto.getBirthday().trim().equals("") || dto.getEmail().trim().equals("")
                || dto.getAddress().trim().equals("")  || dto.getRole().trim().equals("")) {
            throw new InvalidParameterException("Do not leave any information blank");
        }
		
		if (dto.getAge() < 5 || dto.getAge() > 125) {
			throw new InvalidParameterException("Age cannot be less than 5 or greater than 125");
		}
		
		
		String algorithm = "SHA-256";
		String hashedPassword = HashUtil.hashPassword(dto.getPassword().trim(), algorithm);
		dto.setPassword(hashedPassword);
		System.out.println(hashedPassword);
			
			Users createdUser = this.ud.addUser(dto);
			return createdUser;
		
	}
	
	public Users getUser(String username, String password) throws InvalidLoginException, NoSuchAlgorithmException {
		
		logger.info("UserService.getUser() invoked");
		
		Users user = ud.getUser(username);
		// Users user = ud.getUser(access, password);
		
		try {
			
			if (user != null) {
				String algorithm = "SHA-256";
				String hashedInputPassword = HashUtil.hashInputPassword(password.trim(), algorithm);
				System.out.println(hashedInputPassword);
				
				logger.info("hashedInputPassword {}", hashedInputPassword);
				logger.info("user.getPassword {}", user.getPassword());
				
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
	
	public Users updateUser(Users currentUser, Users updatedUserInfo) throws InvalidParameterException {
		
		if (updatedUserInfo.getUsername().trim().equals("") || updatedUserInfo.getPassword().trim().equals("")
                || updatedUserInfo.getFirstName().trim().equals("") || updatedUserInfo.getLastName().trim().equals("")
                || updatedUserInfo.getBirthday().trim().equals("") || updatedUserInfo.getEmail().trim().equals("")
                || updatedUserInfo.getAddress().trim().equals("")  || updatedUserInfo.getRole().trim().equals("")) {
            throw new InvalidParameterException("Do not leave any information blank");
        }
		
		if (updatedUserInfo.getAge() < 5 || updatedUserInfo.getAge() > 125) {
			throw new InvalidParameterException("Age cannot be less than 5 or greater than 125");
		}
		
		int currentUserId = currentUser.getId();
		updatedUserInfo.setId(currentUserId);
		updatedUserInfo.setUsername(currentUser.getUsername());
		updatedUserInfo.setPassword(currentUser.getPassword());
		updatedUserInfo.setRole(currentUser.getRole());
		
		updatedUserInfo = ud.updateUser(currentUserId, updatedUserInfo);
		
		return updatedUserInfo;
	}

}
