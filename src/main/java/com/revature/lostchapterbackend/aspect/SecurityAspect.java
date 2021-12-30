package com.revature.lostchapterbackend.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.revature.lostchapterbackend.model.Users;

@Aspect
@Component
public class SecurityAspect {
	
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

}
