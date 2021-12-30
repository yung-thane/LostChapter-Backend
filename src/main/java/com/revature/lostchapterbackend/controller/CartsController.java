package com.revature.lostchapterbackend.controller;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.lostchapterbackend.model.Carts;
import com.revature.lostchapterbackend.service.CartsService;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class CartsController {

	@Autowired
	private CartsService cs;

	private final String PATTERN = "[0-9]+"; // checks String if it only contains numbers

	@PostMapping(path = "/users/{userId}/cart") // Because a User is connected to a Cart, we can then find the cart Id
												// // by using the User.
	public ResponseEntity<Object> addBookToCart(@PathVariable("userId") String userId,
			@RequestParam("bookId") String bookId, @RequestParam("quantityToBuy") String quantityToBuy) {
		// Aspect or another class for protecting endpoint
		try {
			Carts currentCart = null;
			if (userId.matches(PATTERN) || bookId.matches(PATTERN) || quantityToBuy.matches(PATTERN)) {
				currentCart = cs.addBooksToCart(currentCart, userId, bookId, quantityToBuy);
				return ResponseEntity.status(200).body(currentCart);
			} else {
				throw new NumberFormatException("product id or quantity must be of type int!");
			}
		} catch (NumberFormatException e) {
			return ResponseEntity.status(400).body("boo boo");
		} catch (NoResultException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}

	@GetMapping(path = "/users/{userId}/cart") // endpoint used for displaying all Books in the Cart
	public ResponseEntity<Object> getCartById(@PathVariable("userId") String userId) {
		// Aspect or another class for protecting endpoint
		try {
			if (userId.matches(PATTERN)) {
				Carts getCartById = cs.getCartById(userId);
				return ResponseEntity.status(200).body(getCartById);
			} else {
				throw new NumberFormatException("product id or quantity must be of type int!");
			}
		} catch (NumberFormatException e) {
			return ResponseEntity.status(400).body("boo boo");
		} catch (NoResultException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		}
	}
	
	@DeleteMapping(path = "/users/{userId}/cart")
	public ResponseEntity<Object> delteteProductInCart(@PathVariable("userId") String cartId,
			@RequestParam("bookId") String bookId) throws /* ProductNotFoundException, */ NoResultException {

		try {
			Carts currentCart = null;
			if (cartId.matches(PATTERN) || bookId.matches(PATTERN)) {
				currentCart = cs.delteteProductInCart(currentCart, cartId, bookId);
				return ResponseEntity.status(200).body(currentCart);
			} else {
				throw new NumberFormatException("cart id/product id must be of type int!");
			}
		} catch (NumberFormatException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (NoResultException e) { 
			return ResponseEntity.status(404).body(e.getMessage());
		}/* To Be implemented once merged with Display/Search Product team
			 * catch (BookNotFoundException e) { return
			 * ResponseEntity.status(404).body(e.getMessage()); }
			 */
	}
}
