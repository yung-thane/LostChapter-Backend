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
	
	public static Logger logger = LoggerFactory.getLogger(ValidateUtil.class);
	
	@Autowired
	UserService userService;
	
	static List<String> userRoleList = Arrays.asList("customer", "admin");
	
	public void verifySignUp(SignUpDto user) throws InvalidParameterException {
		logger.info("ValidateUtil.createUser() invoked");
		
		logger.info("check if inputs are blank");
		
		boolean blankInputs = false;
		StringBuilder blankInputStrings = new StringBuilder();
		
		if (StringUtils.isBlank(user.getUsername().trim())) {
			blankInputStrings.append("username ");
			blankInputs = true;
		}
		
		if (StringUtils.isBlank(user.getPassword().trim())) {
			blankInputStrings.append("password ");
			blankInputs = true;
		}
		
		if (StringUtils.isBlank(user.getFirstName().trim())) {
			blankInputStrings.append("firstName ");
			blankInputs = true;
		}
		
		if (StringUtils.isBlank(user.getLastName().trim())) {
			blankInputStrings.append("lastName ");
			blankInputs = true;
		}
		
		if (StringUtils.isBlank(user.getEmail().trim())) {
			blankInputStrings.append("email ");
			blankInputs = true;
			
		}
		
		if (StringUtils.isBlank(user.getBirthday().trim())) {
			blankInputStrings.append("birthday ");
			blankInputs = true;
		}
		
		if (StringUtils.isBlank(user.getAddress().trim())) {
			blankInputStrings.append("address ");
			blankInputs = true;
		}
		
		if (StringUtils.isBlank(user.getRole().trim())) {
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
		
        Users databaseUser = userService.getUserByEmail(user.getEmail());
        
		logger.info("databaseUser: {}", databaseUser);

        if (databaseUser != null) {
            if (StringUtils.equalsAnyIgnoreCase(databaseUser.getEmail().trim(), user.getEmail().trim())) {

                throw new InvalidParameterException("Email already exist.");
            }
        }
		
		logger.info("email verification");

		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

		if (!user.getEmail().matches(regex)) {
			throw new InvalidParameterException("Invalid Email.");
		}
		
	}

}
