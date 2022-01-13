package com.revature.lostchapterbackend.usertests;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;

import com.revature.lostchapterbackend.aspect.SecurityAspect;
import com.revature.lostchapterbackend.model.Users;

public class SecurityAspectUnitTests {

	@Mock
	private ProceedingJoinPoint pjp;
	
	@Mock
	private HttpServletRequest req; 
	
	@InjectMocks
	private SecurityAspect sut; 
	
	@SuppressWarnings("deprecation")
	@BeforeEach 
	public void Setup() {
		
		this.sut = new SecurityAspect(); 
		MockitoAnnotations.initMocks(this);
		
	}
	
	@Test
	public void test_AuthorizedUser_not_logged_in_negative() throws Throwable {
		
		MockHttpSession mockSession = new MockHttpSession(); 	
		mockSession.setAttribute("currentUser", null);
		Mockito.when(req.getSession()).thenReturn(mockSession);
		
		
		ResponseEntity<?> responseEntity = (ResponseEntity<?>) sut.protectEndpointUsersOnly(pjp);
		
		Assertions.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		Assertions.assertEquals("You are not currently logged in", responseEntity.getBody());
		
	}
	
	@Test
	public void test_AuthorizedUser_positive() throws Throwable {
		
		Users testUser = new Users(); 
		
		MockHttpSession mockSession = new MockHttpSession();
		mockSession.setAttribute("currentUser", testUser);
		Mockito.when(req.getSession()).thenReturn(mockSession);
		Mockito.when(pjp.proceed()).thenReturn(true);
		
		Assertions.assertEquals(true, sut.protectEndpointUsersOnly(pjp));
		
	}
	
	@Test
	public void test_Customer_positive() throws Throwable {
		
		Users testUser = new Users();
		testUser.setRole("Customer");
		
		MockHttpSession mockSession = new MockHttpSession(); 
		mockSession.setAttribute("currentUser", testUser);
		Mockito.when(req.getSession()).thenReturn(mockSession);
		Mockito.when(pjp.proceed()).thenReturn(true);
		
		Assertions.assertEquals(true, sut.protectEndpointCustomerOnly(pjp));
		
	}
	
	@Test
	public void test_Customer_not_logged_in_negative() throws Throwable {
		
		MockHttpSession mockSession = new MockHttpSession();
		mockSession.setAttribute("currentUser", null);
		Mockito.when(req.getSession()).thenReturn(mockSession);
		
		ResponseEntity<?> responseEntity = (ResponseEntity<?>) sut.protectEndpointCustomerOnly(pjp);
		
		Assertions.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		Assertions.assertEquals("You are not currently logged in", responseEntity.getBody());
		
	}
	
	@Test
	public void test_Customer_not_customer_role_negative() throws Throwable {
		
		Users testUser = new Users();
		testUser.setRole("potato");
		
		MockHttpSession mockSession = new MockHttpSession(); 
		mockSession.setAttribute("currentUser", testUser);
		Mockito.when(req.getSession()).thenReturn(mockSession);
		
		ResponseEntity<?> responseEntity = (ResponseEntity<?>) sut.protectEndpointCustomerOnly(pjp);
		
		Assertions.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		Assertions.assertEquals("You are logged in, but only customers are allowed to access this endpoint", responseEntity.getBody());
		
	}
	
	@Test
	public void test_Admin_positive() throws Throwable {
		
		Users testUser = new Users();
		testUser.setRole("Admin");
		MockHttpSession mockSession = new MockHttpSession(); 
		mockSession.setAttribute("currentUser", testUser);
		Mockito.when(req.getSession()).thenReturn(mockSession);
		Mockito.when(pjp.proceed()).thenReturn(true);
		
		Assertions.assertEquals(true, sut.protectEndpointAdminOnly(pjp));
		
	}
	
	@Test
	public void test_Admin_not_logged_in_negative() throws Throwable {
		
		MockHttpSession mockSession = new MockHttpSession(); 
		mockSession.setAttribute("currentUser", null);
		Mockito.when(req.getSession()).thenReturn(mockSession);
		
		ResponseEntity<?> responseEntity = (ResponseEntity<?>) sut.protectEndpointAdminOnly(pjp);
		
		Assertions.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		Assertions.assertEquals("You are not currently logged in", responseEntity.getBody());
		
		
	}
	
	@Test
	public void test_Admin_role_not_Admin_negative() throws Throwable {
		
		Users testUser = new Users();
		testUser.setRole("potato");
		
		MockHttpSession mockSession = new MockHttpSession(); 
		mockSession.setAttribute("currentUser", testUser);
		Mockito.when(req.getSession()).thenReturn(mockSession);
		
		ResponseEntity<?> responseEntity = (ResponseEntity<?>) sut.protectEndpointAdminOnly(pjp);
		
		Assertions.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		Assertions.assertEquals("You are logged in, but only admins are allowed to access this endpoint", responseEntity.getBody());
		
	}
	
	@Test
	public void test_AdminAndCustomer_Admin_positive() throws Throwable {
		
		Users testUser = new Users();
		testUser.setRole("Admin");
		MockHttpSession mockSession = new MockHttpSession(); 
		mockSession.setAttribute("currentUser", testUser);
		Mockito.when(req.getSession()).thenReturn(mockSession);
		Mockito.when(pjp.proceed()).thenReturn(true);
		
		Assertions.assertEquals(true, sut.protectEndpointAdminAndConsumerOnly(pjp));
		
	}
	
	@Test
	public void test_AdminAndCustomer_Customer_positive() throws Throwable {
		
		Users testUser = new Users();
		testUser.setRole("Customer");
		MockHttpSession mockSession = new MockHttpSession(); 
		mockSession.setAttribute("currentUser", testUser);
		Mockito.when(req.getSession()).thenReturn(mockSession);
		Mockito.when(pjp.proceed()).thenReturn(true);
		
		Assertions.assertEquals(true, sut.protectEndpointAdminAndConsumerOnly(pjp));
		
	}
	
	@Test
	public void test_AdminAndCustomer_not_logged_in_negative() throws Throwable {
		
		MockHttpSession mockSession = new MockHttpSession(); 
		mockSession.setAttribute("currentUser", null);
		Mockito.when(req.getSession()).thenReturn(mockSession);
		
		ResponseEntity<?> responseEntity = (ResponseEntity<?>) sut.protectEndpointAdminAndConsumerOnly(pjp);
		
		Assertions.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		Assertions.assertEquals("You are not currently logged in", responseEntity.getBody());
		
	}
	
	@Test
	public void test_AdminAndCustomer_role_not_admin_or_customer_negative() throws Throwable {
		
		Users testUser = new Users();
		testUser.setRole("potato");
		
		MockHttpSession mockSession = new MockHttpSession(); 
		mockSession.setAttribute("currentUser", testUser);
		Mockito.when(req.getSession()).thenReturn(mockSession);
		
		ResponseEntity<?> responseEntity = (ResponseEntity<?>) sut.protectEndpointAdminAndConsumerOnly(pjp);
		
		Assertions.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		Assertions.assertEquals("You are logged in, but only admins and customers are allowed to access this endpoint", responseEntity.getBody());
		
	}
	
	
}
