package com.revature.lostchapterbackend.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.revature.lostchapterbackend.model.Users;

@Aspect
@Component
public class SecurityAspect {

	private Logger logger = LoggerFactory.getLogger(SecurityAspect.class);

	@Autowired
	private HttpServletRequest req;

	@Around("@annotation(com.revature.lostchapterbackend.annotation.AuthorizedUser)")
	public Object protectEndpointUsersOnly(ProceedingJoinPoint pjp) throws Throwable {

		HttpSession session = req.getSession();

		Users currentlyLoggedInUser = (Users) session.getAttribute("currentUser");

		if (currentlyLoggedInUser == null) {
			return ResponseEntity.status(401).body("You are not currently logged in");
		}

		Object returnValue = pjp.proceed();
		return returnValue;
	}

	@Around("@annotation(com.revature.lostchapterbackend.annotation.Customer)")
	public Object protectEndpointCustomerOnly(ProceedingJoinPoint pjp) throws Throwable {

		HttpSession session = req.getSession();

		Users currentlyLoggedInUser = (Users) session.getAttribute("currentUser");

		if (currentlyLoggedInUser == null) {
			return ResponseEntity.status(401).body("You are not currently logged in");
		}

		if (!currentlyLoggedInUser.getRole().equals("Customer")) {
			return ResponseEntity.status(401)
					.body("You are logged in, but only customers are allowed to access this endpoint");
		}

		Object returnValue = pjp.proceed();
		return returnValue;

	}

	@Around("@annotation(com.revature.lostchapterbackend.annotation.Admin)")
	public Object protectEndpointAdminOnly(ProceedingJoinPoint pjp) throws Throwable {

		HttpSession session = req.getSession();

		Users currentlyLoggedInUser = (Users) session.getAttribute("currentUser");

		if (currentlyLoggedInUser == null) {
			return ResponseEntity.status(401).body("You are not currently logged in");
		}

		if (!currentlyLoggedInUser.getRole().equals("Admin")) {
			return ResponseEntity.status(401)
					.body("You are logged in, but only admins are allowed to access this endpoint");
		}

		Object returnValue = pjp.proceed();
		return returnValue;

	}

	@Around("@annotation(com.revature.lostchapterbackend.annotation.AdminAndCustomer)")
	public Object protectEndpointAdminAndConsumerOnly(ProceedingJoinPoint pjp) throws Throwable {

		HttpSession session = req.getSession();

		Users currentlyLoggedInUser = (Users) session.getAttribute("currentUser");

		if (currentlyLoggedInUser == null) {
			return ResponseEntity.status(401).body("You are not currently logged in");
		}

		if (!currentlyLoggedInUser.getRole().equals("Admin") && (!currentlyLoggedInUser.getRole().equals("Customer"))) {
			return ResponseEntity.status(401)
					.body("You are logged in, but only admins and customers are allowed to access this endpoint");
		}

		Object returnValue = pjp.proceed();
		return returnValue;

	}

}
