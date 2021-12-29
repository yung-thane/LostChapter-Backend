package com.revature.lostchapterbackend.utility;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.revature.lostchapterbackend.dto.LoginDto;
import com.revature.lostchapterbackend.dto.SignUpDto;
import com.revature.lostchapterbackend.exceptions.InvalidLoginException;
import com.revature.lostchapterbackend.exceptions.InvalidParameter;
import com.revature.lostchapterbackend.model.Users;
import com.revature.lostchapterbackend.service.UserService;

import io.micrometer.core.instrument.util.StringUtils;

public class ValidateUtil {
	
	Logger logger = LoggerFactory.getLogger(ValidateUtil.class);
	
	@Autowired
	UserService userService;
	
	static List<String> userRoleList = Arrays.asList("Customer", "Employee");
	
	public void verifySignUp(Users user) throws InvalidParameter {
		logger.info("ValidateUtil.createUser() invoked");
		
		logger.info("check if inputs are blank");
		
		boolean blankInputs = false;
		StringBuilder blankInputStrings = new StringBuilder();
		
		if (StringUtils.isBlank(user.getUsername().trim())) {
			blankInputStrings.append("username");
			blankInputs = true;
		}
		
		if (StringUtils.isBlank(user.getPassword().trim())) {
			blankInputStrings.append("password");
			blankInputs = true;
		}
		
		if (StringUtils.isBlank(user.getFirstName().trim())) {
			blankInputStrings.append("firstName");
			blankInputs = true;
		}
		
		if (StringUtils.isBlank(user.getLastName().trim())) {
			blankInputStrings.append("lastName");
			blankInputs = true;
		}
		
		if (StringUtils.isBlank(user.getEmail().trim())) {
			blankInputStrings.append("email");
			blankInputs = true;
		}
		
		if (StringUtils.isBlank(user.getBirthday().trim())) {
			blankInputStrings.append("birthday");
			blankInputs = true;
		}
		
		if (StringUtils.isBlank(user.getAddress().trim())) {
			blankInputStrings.append("address");
			blankInputs = true;
		}
		
		if (StringUtils.isBlank(user.getRole().trim())) {
			blankInputStrings.append("role");
			blankInputs = true;
		}
		
		logger.info("Check if email already exist");
		
		Users databaseUser = userService.getUserByEmail(user.getEmail());
		
		boolean emailExistsBoolean = false;
		StringBuilder emailExistsString = new StringBuilder();
		
		logger.debug("databaseUser {}", databaseUser);
		
		if (databaseUser != null) {
							
			throw new InvalidParameter("This email already exists!");
		}
		
	}

}
