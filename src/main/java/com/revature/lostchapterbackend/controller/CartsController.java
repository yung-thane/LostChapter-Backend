package com.revature.lostchapterbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class CartsController {

//	@Autowired
//	private CartsService cs;
//
//	@Autowired
//	private CartsDAO cd; // testing dao purposes

	@PostMapping(path = "/users/{userId}/cart") // Because a User is connected to a Cart, we can then find the cart Id												// by using the User.
	public ResponseEntity<Object> addBookToCart(@PathVariable("userId") String userId,
			@RequestParam("bookId") String bookId, @RequestParam("quantityToBuy") String quantityToBuy) {
		
		return ResponseEntity.status(200).body("Hello");

	}
	
	@GetMapping(path ="/test")
	public ResponseEntity<Object> testing() {
		return ResponseEntity.status(200).body("Hello");
		//d908aa3b-115c-4acd-a7d0-3cf199b0354c
	}

}
