package com.revature.lostchapterbackend.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.lostchapterbackend.utility.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping(path = "/signup")
    public ResponseEntity<Object> signUp(@RequestBody SignUpDto dto) throws InvalidParameterException, NoSuchAlgorithmException, InvalidLoginException {

        try {

            validateUtil.verifySignUp(dto);

            Users user = this.us.createUser(dto);

            String token = jwtUtil.generateToken(user.getUsername());

            return ResponseEntity.status(201).body(Collections.singletonMap("jwt-token", token));

        } catch (InvalidParameterException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto dto) throws NoSuchAlgorithmException {

        try {
            Users user = this.us.getUser(dto.getUsername(), dto.getPassword());

            String token = jwtUtil.generateToken(user.getUsername());

            return ResponseEntity.status(200).body(Collections.singletonMap("jwt-token", token));
        } catch (InvalidLoginException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<Object> logout(@RequestHeader(name = "Authorization") String token) {
        String jwt = token.substring(7);

        // Can we revoke jwt tokens? Since it's stateless???

        return ResponseEntity.status(200).body("Successfully logged out");
    }

    @GetMapping(path = "/loginstatus")
    public ResponseEntity<Object> checkLoginStatus(@RequestHeader(name = "Authorization") String token) {
        // @Request header allows us to grab the token,
        // Token validated by the JWTFilter

        // Format of jwt puts the token at this spot
        // Formatting is checked in the filter
        String jwt = token.substring(7);

        String username = jwtUtil.validateTokenAndGetUsername(jwt);
        try {
            Users currentUser = us.getUserByUsername(username);
            return ResponseEntity.status(200).body(currentUser);
        } catch (InvalidParameterException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/user")
    public ResponseEntity<Object> deleteUserById(@RequestHeader(name = "Authorization") String token) throws UserNotFoundException {
        try {
            String jwt = token.substring(7);

            String username = jwtUtil.validateTokenAndGetUsername(jwt);

            Users currentUser = us.getUserByUsername(username);
            int id = currentUser.getId();
            us.deleteUserById(currentUser);

            return ResponseEntity.status(200).body("This user has been successfully deleted by id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping(path = "/user")
    public ResponseEntity<Object> updateUser(@RequestBody Users user, @RequestHeader(name = "Authorization") String token) throws UserNotFoundException {

        try {
            String jwt = token.substring(7);

            validateUtil.verifyUpdateUser(user);
            String username = jwtUtil.validateTokenAndGetUsername(jwt);
            Users currentlyLoggedInUser = us.getUserByUsername(username);

            System.out.println(currentlyLoggedInUser);

            Users userToBeUpdated = us.updateUser(currentlyLoggedInUser, user);
            return ResponseEntity.status(200).body(userToBeUpdated);
        } catch (InvalidParameterException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
