package com.revature.lostchapterbackend.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.lostchapterbackend.dto.LoginDto;
import com.revature.lostchapterbackend.dto.SignUpDto;
import com.revature.lostchapterbackend.exceptions.InvalidLoginException;
import com.revature.lostchapterbackend.exceptions.UserNotFoundException;
import com.revature.lostchapterbackend.model.Users;
import com.revature.lostchapterbackend.service.UserService;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AuthenticationController {
	
	@Autowired
	private UserService us;
	
	@Autowired
	private HttpServletRequest req;
	
	@PostMapping(path = "/signup")
	public ResponseEntity<Object> signUp(@RequestBody SignUpDto dto) {
		
		try {
			Users user = this.us.createUser(dto);
			
			return ResponseEntity.status(201).body(user);
			
		} catch (InvalidLoginException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@PostMapping(path = "/login")
	public ResponseEntity<Object> login(@RequestBody LoginDto dto) {
		
		try {
			Users user = this.us.getUser(dto.getAccess(), dto.getPassword());
			
			HttpSession session = req.getSession();
			session.setAttribute("currentUser", user);
			
			return ResponseEntity.status(200).body(user);
		} catch (InvalidLoginException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@PostMapping(path = "/logout")
	public ResponseEntity<Users> logout() {
		req.getSession().invalidate();
		
		return ResponseEntity.status(200).body(new Users());
	}
	
	@GetMapping(path = "/loginstatus")
	public ResponseEntity<Object> checkLoginStatus() {
		Users currentlyLoggedInUser = (Users) req.getSession().getAttribute("currentUser");
		
		if (currentlyLoggedInUser != null) {
			return ResponseEntity.status(200).body(currentlyLoggedInUser);
		}
		
		return ResponseEntity.status(200).body(new Users());
	}
	
	@DeleteMapping(path = "/delete")
	public ResponseEntity<Object> deleteUserById() throws UserNotFoundException {
		
		HttpSession session = req.getSession();
		Users currentlyLoggedInUser = (Users) session.getAttribute("currentUser");
		
		if (currentlyLoggedInUser == null) {
			throw new UserNotFoundException("This user does not exist or is not logged in");
		}
		
		int id = currentlyLoggedInUser.getId();
		us.deleteUserById(currentlyLoggedInUser);
		
		return ResponseEntity.status(200).body("This user has been successfully deleted by id: " + id);
	}

}
