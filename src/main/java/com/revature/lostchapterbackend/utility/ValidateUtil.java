package com.revature.lostchapterbackend.utility;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.revature.lostchapterbackend.dto.SignUpDto;
import com.revature.lostchapterbackend.exceptions.InvalidParameterException;
import com.revature.lostchapterbackend.model.Users;
import com.revature.lostchapterbackend.service.UserService;

public class ValidateUtil {

	public Logger logger = LoggerFactory.getLogger(ValidateUtil.class);

	@Autowired
	UserService userService;

	static List<String> userRoleList = Arrays.asList("Customer", "Admin");

	public void verifySignUp(SignUpDto user) throws InvalidParameterException {
		logger.info("ValidateUtil.createUser() invoked");

		logger.info("check if inputs are blank");

		boolean blankInputs = false;
		StringBuilder blankInputStrings = new StringBuilder();

		if (user.getUsername() == null || StringUtils.isBlank(user.getUsername().trim())) {
			blankInputStrings.append("username ");
			blankInputs = true;
		}

		if (user.getPassword() == null || StringUtils.isBlank(user.getPassword().trim())) {
			blankInputStrings.append("password ");
			blankInputs = true;
		}

		if (user.getFirstName() == null || StringUtils.isBlank(user.getFirstName().trim())) {
			blankInputStrings.append("firstName ");
			blankInputs = true;
		}

		if (user.getLastName() == null || StringUtils.isBlank(user.getLastName().trim())) {
			blankInputStrings.append("lastName ");
			blankInputs = true;
		}

		if (user.getEmail() == null || StringUtils.isBlank(user.getEmail().trim())) {
			blankInputStrings.append("email ");
			blankInputs = true;

		}

		if (user.getBirthday() == null || StringUtils.isBlank(user.getBirthday().trim())) {
			blankInputStrings.append("birthday ");
			blankInputs = true;
		}

		if (user.getAddress() == null || StringUtils.isBlank(user.getAddress().trim())) {
			blankInputStrings.append("address ");
			blankInputs = true;
		}

		if (user.getRole() == null || StringUtils.isBlank(user.getRole().trim())) {
			blankInputStrings.append("role ");
			blankInputs = true;
		}

		if (blankInputs) {
			blankInputStrings.append("cannot be blank.");
			throw new InvalidParameterException(blankInputStrings.toString());
		}

		logger.info("Check if email and username already exist");

		logger.debug("user.getEmail(): {}", user.getEmail());
		logger.debug("userService: {}", userService);

		Users databaseUserEmail = userService.getUserByEmail(user.getEmail());

		Users databaseUserUsername = userService.getUserByUsername(user.getUsername());

		if (databaseUserEmail != null) {
			if (StringUtils.equalsAnyIgnoreCase(databaseUserEmail.getEmail().trim(), user.getEmail().trim())) {

				throw new InvalidParameterException("Email already exist.");
			}
		}

		if (databaseUserUsername != null) {
			if (StringUtils.equalsAnyIgnoreCase(databaseUserUsername.getUsername().trim(), user.getUsername().trim())) {

				throw new InvalidParameterException("Username already exist.");
			}
		}

		logger.info("email verification");

		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

		if (!user.getEmail().matches(regex)) {
			throw new InvalidParameterException("Invalid Email.");
		}

	}

	public void verifyUpdateUser(Users user) throws InvalidParameterException {
		logger.info("ValidateUtil.createUser() invoked");

		logger.info("check if inputs are blank");

		boolean blankInputs = false;
		StringBuilder blankInputStrings = new StringBuilder();

		if (user.getUsername() == null || StringUtils.isBlank(user.getUsername().trim())) {
			blankInputStrings.append("username ");
			blankInputs = true;
		}

		if (user.getPassword() == null || StringUtils.isBlank(user.getPassword().trim())) {
			blankInputStrings.append("password ");
			blankInputs = true;
		}

		if (user.getFirstName() == null || StringUtils.isBlank(user.getFirstName().trim())) {
			blankInputStrings.append("firstName ");
			blankInputs = true;
		}

		if (user.getLastName() == null || StringUtils.isBlank(user.getLastName().trim())) {
			blankInputStrings.append("lastName ");
			blankInputs = true;
		}

		if (user.getEmail() == null || StringUtils.isBlank(user.getEmail().trim())) {
			blankInputStrings.append("email ");
			blankInputs = true;

		}

		if (user.getBirthday() == null || StringUtils.isBlank(user.getBirthday().trim())) {
			blankInputStrings.append("birthday ");
			blankInputs = true;
		}

		if (user.getAddress() == null || StringUtils.isBlank(user.getAddress().trim())) {
			blankInputStrings.append("address ");
			blankInputs = true;
		}

		if (user.getRole() == null || StringUtils.isBlank(user.getRole().trim())) {
			blankInputStrings.append("role ");
			blankInputs = true;
		}

		if (blankInputs) {
			blankInputStrings.append("cannot be blank.");
			throw new InvalidParameterException(blankInputStrings.toString());
		}

		logger.info("Check if email already exist");

		logger.info("user.getEmail(): {}", user.getEmail());
		logger.info("userService: {}", userService);

		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

		if (!user.getEmail().matches(regex)) {
			throw new InvalidParameterException("Invalid Email.");
		}

	}

}
