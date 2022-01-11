package com.revature.lostchapterbackend.service;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.dao.UserDao;
import com.revature.lostchapterbackend.dto.SignUpDto;
import com.revature.lostchapterbackend.exceptions.InvalidLoginException;
import com.revature.lostchapterbackend.exceptions.InvalidParameterException;
import com.revature.lostchapterbackend.exceptions.UserNotFoundException;
import com.revature.lostchapterbackend.model.Carts;
import com.revature.lostchapterbackend.model.Users;
import com.revature.lostchapterbackend.utility.HashUtil;

@Service
public class UserService {

	@Autowired
	private UserDao ud;

	private Logger logger = LoggerFactory.getLogger(UserService.class);

	public UserService(UserDao ud) {
		this.ud = ud;
	}

	public Users createUser(SignUpDto dto)
			throws InvalidLoginException, InvalidParameterException, NoSuchAlgorithmException {

		if (dto.getUsername().trim().equals("") || dto.getPassword().trim().equals("")
				|| dto.getFirstName().trim().equals("") || dto.getLastName().trim().equals("")
				|| dto.getBirthday().trim().equals("") || dto.getEmail().trim().equals("")
				|| dto.getAddress().trim().equals("") || dto.getRole().trim().equals("")) {
			throw new InvalidParameterException("Do not leave any information blank");
		}
		
		if (dto.getUsername().length() > 255 || dto.getFirstName().length() > 255 || dto.getLastName().length() > 255 || dto.getEmail().length() > 255) {
			throw new InvalidParameterException("Character lengths shouldn't extend over 255 characters");
		}

		if (dto.getAge() < 5 || dto.getAge() > 125) {
			throw new InvalidParameterException("Age cannot be less than 5 or greater than 125");
		}

		Set<String> validRole = new HashSet<>();
		validRole.add("Customer");
		validRole.add("Admin");

		if (!validRole.contains(dto.getRole())) {
			throw new InvalidParameterException("You can only sign up as a Customer or an Admin");
		}

		String algorithm = "SHA-256";
		String hashedPassword = HashUtil.hashPassword(dto.getPassword().trim(), algorithm);
		dto.setPassword(hashedPassword);

		Carts c = null;
		Users createdUser = this.ud.addUser(dto, c);
		return createdUser;

	}

	public Users getUser(String username, String password) throws InvalidLoginException, NoSuchAlgorithmException {

		logger.info("UserService.getUser() invoked");

		Users user = ud.getUser(username);

		try {

			if (user != null) {
				String algorithm = "SHA-256";
				String hashedInputPassword = HashUtil.hashInputPassword(password.trim(), algorithm);

				Boolean correctPassword = hashedInputPassword.equals(user.getPassword());

				if (correctPassword) {
					return user;
				} else {
					throw new InvalidLoginException("Username and/or password is incorrect");
				}
			} else {
				throw new InvalidLoginException("Username and/or password is incorrect");
			}
		} catch (DataAccessException e) {
			throw new InvalidLoginException("Username and/or password is incorrect");
		}
	}

	public void deleteUserById(Users currentUser) throws UserNotFoundException {

		if (currentUser != null) {
			int currentUserId = currentUser.getId();
			ud.deleteUserById(currentUserId);
		} else {
			throw new UserNotFoundException("Current user is null");
		}
	}

	public Users getUserByEmail(String email) throws InvalidParameterException {
		logger.info("UserService.getUserByEmail() invoked");

		if (email == null) {
			throw new InvalidParameterException("Email is Null");
		}

		Users users = this.ud.getUserByEmail(email);

		return users;
	}

	public Users updateUser(Users currentUser, Users updatedUserInfo) throws InvalidParameterException {

		if (updatedUserInfo.getUsername().trim().equals("") || updatedUserInfo.getPassword().trim().equals("")
				|| updatedUserInfo.getFirstName().trim().equals("") || updatedUserInfo.getLastName().trim().equals("")
				|| updatedUserInfo.getBirthday().trim().equals("") || updatedUserInfo.getEmail().trim().equals("")
				|| updatedUserInfo.getAddress().trim().equals("") || updatedUserInfo.getRole().trim().equals("")) {
			throw new InvalidParameterException("Do not leave any information blank");
		}
		
		if (updatedUserInfo.getUsername().length() > 255 || updatedUserInfo.getFirstName().length() > 255 || updatedUserInfo.getLastName().length() > 255 || updatedUserInfo.getEmail().length() > 255) {
			throw new InvalidParameterException("Character lengths shouldn't extend over 255 characters");
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

	public Users getUserByUsername(String username) throws InvalidParameterException {
		logger.info("UserService.getUserByUsername() invoked");

		if (username == null) {
			throw new InvalidParameterException("username is Null");
		}

		Users users = this.ud.getUser(username);

		return users;
	}

}
