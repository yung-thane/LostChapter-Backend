package com.revature.lostchapterbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class CartsController {

//	@Autowired
//	private CartsService cs;
//
//	@Autowired
//	private CartsDAO cd; // testing dao purposes
	
	private final String PATTERN = "[0-9]+"; // checks String if it only contains numbers

	@PostMapping(path = "/users/{userId}/cart") // Because a User is connected to a Cart, we can then find the cart Id												// by using the User.
	public ResponseEntity<Object> addBookToCart(@PathVariable("userId") String userId,
			@RequestParam("bookId") String bookId, @RequestParam("quantityToBuy") String quantityToBuy) {
		//Aspect or another class for protecting endpoint
		
		if (userId.matches(PATTERN) || bookId.matches(PATTERN) || quantityToBuy.matches(PATTERN)) {
			
		}
		
		return ResponseEntity.status(200).body("Hello");

	}
	
	@GetMapping(path ="/test")
	public ResponseEntity<Object> testing(@RequestParam("bookId") String number) {
		
		return ResponseEntity.status(200).body("bookId");
	}

}
