package com.revature.lostchapterbackend.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.lostchapterbackend.dto.SignUpDto;
import com.revature.lostchapterbackend.exceptions.InvalidLoginException;
import com.revature.lostchapterbackend.model.Users;
import com.revature.lostchapterbackend.service.UserService;

@RestController
public class AuthenticationController {
	
	@Autowired
	private UserService us;
	
	@Autowired
	private HttpServletRequest req;
	
	@PostMapping(path = "/signup")
	public ResponseEntity<Object> signUp(@RequestBody SignUpDto dto) {
		
		try {
			Users user = this.us.createUser(dto);
			
			HttpSession session = req.getSession();
			session.setAttribute("currentUser", user);
			
			return ResponseEntity.status(201).body(user);
			
		} catch (InvalidLoginException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

}
