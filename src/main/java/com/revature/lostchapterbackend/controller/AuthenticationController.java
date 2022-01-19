package com.revature.lostchapterbackend.controller;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.lostchapterbackend.dto.LoginDto;
import com.revature.lostchapterbackend.dto.SignUpDto;
import com.revature.lostchapterbackend.exceptions.InvalidLoginException;
import com.revature.lostchapterbackend.exceptions.InvalidParameterException;
import com.revature.lostchapterbackend.exceptions.UserNotFoundException;
import com.revature.lostchapterbackend.model.Users;
import com.revature.lostchapterbackend.service.UserService;
import com.revature.lostchapterbackend.utility.ValidateUtil;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AuthenticationController {
	
	@Autowired
	private UserService us;
	
	@Autowired
	private HttpServletRequest req;
	
	@Autowired
	private ValidateUtil validateUtil; 
	
	@PostMapping(path = "/signup")
	public ResponseEntity<Object> signUp(@RequestBody SignUpDto dto) throws InvalidParameterException, NoSuchAlgorithmException, InvalidLoginException {
		
		try {
			
			validateUtil.verifySignUp(dto);
			
			Users user = this.us.createUser(dto);
			
			return ResponseEntity.status(201).body("Successfully Sign up");
			
		} catch (InvalidParameterException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@PostMapping(path = "/login")
	public ResponseEntity<Object> login(@RequestBody LoginDto dto) throws NoSuchAlgorithmException {
		
		try {
			Users user = this.us.getUser(dto.getUsername(), dto.getPassword());
			
			HttpSession session = req.getSession();
			session.setAttribute("currentUser", user);
			
			return ResponseEntity.status(200).body(user);
		} catch (InvalidLoginException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@PostMapping(path = "/logout")
	public ResponseEntity<Object> logout() {
		req.getSession().invalidate();
		
		return ResponseEntity.status(200).body("Successfully logged out");
	}
	
	@GetMapping(path = "/loginstatus")
	public ResponseEntity<Object> checkLoginStatus() {
		Users currentlyLoggedInUser = (Users) req.getSession().getAttribute("currentUser");
		
		if (currentlyLoggedInUser != null) {
			return ResponseEntity.status(200).body(currentlyLoggedInUser);
		}
		
		return ResponseEntity.status(400).body("No one is currently logged in");
	}
	
	@DeleteMapping(path = "/user")
	public ResponseEntity<Object> deleteUserById() throws UserNotFoundException {
		try {
			HttpSession session = req.getSession();
			Users currentlyLoggedInUser = (Users) session.getAttribute("currentUser");
			
			if (currentlyLoggedInUser == null) {
				throw new UserNotFoundException("This user does not exist or is not logged in");
			}
			
			int id = currentlyLoggedInUser.getId();
			us.deleteUserById(currentlyLoggedInUser);
			req.getSession().invalidate();
			
			return ResponseEntity.status(200).body("This user has been successfully deleted by id: " + id);
		}
		catch(UserNotFoundException e) {
			
			return ResponseEntity.status(400).body(e.getMessage());
			
		}
	}
	
	@PutMapping(path = "/user")
	public ResponseEntity<Object> updateUser(@RequestBody Users user) throws UserNotFoundException {
		
		try {
			validateUtil.verifyUpdateUser(user);
			HttpSession session = req.getSession();
			Users currentlyLoggedInUser = (Users) session.getAttribute("currentUser");
			
			Users userToBeUpdated = us.updateUser(currentlyLoggedInUser, user);
			session.setAttribute("currentUser", userToBeUpdated);
			return ResponseEntity.status(200).body(userToBeUpdated);
		} catch (InvalidParameterException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

}
