package com.revature.lostchapterbackend.usertests;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import com.revature.lostchapterbackend.dao.UserDao;
import com.revature.lostchapterbackend.dto.SignUpDto;
import com.revature.lostchapterbackend.exceptions.InvalidLoginException;
import com.revature.lostchapterbackend.exceptions.InvalidParameterException;
import com.revature.lostchapterbackend.exceptions.UserNotFoundException;
import com.revature.lostchapterbackend.model.Users;
import com.revature.lostchapterbackend.service.UserService;

//@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

@ActiveProfiles("UserService-test")
@SpringBootTest
public class UserServiceTest {
	@Autowired
	private UserService us;
	
	
	@Autowired
	private UserDao ud;
	
	@Test
	public void testCreateUser_positive() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		Users user = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user.setId(1);
		
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		
		Mockito.when(ud.addUser(createdUser)).thenReturn(user);
		
		Users actual = us.createUser(createdUser);
		
		Users expected = us.createUser(new SignUpDto("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer"));
		expected.setId(1);
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void testCreateUserBlankInput_Username_negative1() {
		SignUpDto createdUser = new SignUpDto("  ", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			us.createUser(createdUser);
		});
	}
	
	@Test
	public void testCreateUserBlankInput_Password_negative2() {
		SignUpDto createdUser = new SignUpDto("JDoe", "  ", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			us.createUser(createdUser);
		});
	}
	
	@Test
	public void testCreateUserBlankInput_FirstName_negative3() {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "  ", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			us.createUser(createdUser);
		});
	}
	
	@Test
	public void testCreateUserBlankInput_LastName_negative4() {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "  ", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			us.createUser(createdUser);
		});
	}
	
	@Test
	public void testCreateUserBlankInput_UnderAgeRange_negative5() {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", -3, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			us.createUser(createdUser);
		});
	}
	
	@Test
	public void testCreateUserBlankInput_OverAgeRange_negative6() {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", 175, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			us.createUser(createdUser);
		});
	}
	
	@Test
	public void testCreateUserBlankInput_Email_negative7() {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", 24, "  ", "01/1/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			us.createUser(createdUser);
		});
	}
	
	@Test
	public void testCreateUserBlankInput_BirthDay_negative8() {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "   ", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			us.createUser(createdUser);
		});
	}
	
	@Test
	public void testCreateUserBlankInput_Address_negative9() {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "  ", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			us.createUser(createdUser);
		});
	}
	
	@Test
	public void testCreateUserBlankInput_Role_negative10() {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "  ");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			us.createUser(createdUser);
		});
	}
	
//	@Test
//	public void testGetUser_positive() throws NoSuchAlgorithmException, InvalidLoginException {
//		Users user = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
//		user.setId(1);
//		
//		System.out.println("password: " + user.getPassword());
//		
//		Mockito.when(ud.getUser("JDoe")).thenReturn(user);
//		//hash: 0B14D501A594442A01C6859541BCB3E8164D183D32937B851835442F69D5C94E
//		
//		Users actual = us.getUser("JDoe", "password1");
//		actual.setId(1);
//		
//		Users expected = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
//		expected.setId(1);
//		
//		Assertions.assertEquals(expected, actual);
//	}
	
	@Test
	public void testDeleteUserByID_positive() throws UserNotFoundException {
		Users user = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user.setId(1);
		
		us.deleteUserById(user);
	}
	
	@Test
	public void testDeleteUserByID_IDIsNull_negative() throws UserNotFoundException {
		Assertions.assertThrows(UserNotFoundException.class, () -> {
			us.deleteUserById(null);
		});
	}
	
	@Test
	public void testGetUserByEmail_positive() throws InvalidParameterException {
		Users user = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user.setId(1);
		
		Mockito.when(ud.getUserByEmail("jdoe@gmail.com")).thenReturn(user);
		
		Users actual = us.getUserByEmail("jdoe@gmail.com");
		
		Users expected = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		expected.setId(1);
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetUserByEmail_EmailIsNull_negative() {
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			us.getUserByEmail(null);
		});
	}
	
	@Test
	public void testUpdateUser_ChangeName_positive() throws InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user1.setId(1);
		
		Users user2 = new Users("JDoe", "password1", "Jane", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		Users actual = us.updateUser(user1, user2);
		
		Users expected = new Users("JDoe", "password1", "Jane", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		expected.setId(1);
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void testUpdateUserCannotChange_Username_positive() throws InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user1.setId(1);
		
		Users user2 = new Users("JaneD", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user1);
		
		Users actual = us.updateUser(user1, user2);
		
		Users expected = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		expected.setId(1);
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void testUpdateUserCannotChange_Password_positive() throws InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user1.setId(1);
		
		Users user2 = new Users("JDoe", "pass", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user1);
		
		Users actual = us.updateUser(user1, user2);
		
		Users expected = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		expected.setId(1);
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void testUpdateUserCannotChange_Role_positive() throws InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user1.setId(1);
		
		Users user2 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Admin");
		user2.setId(1);
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user1);
		
		Users actual = us.updateUser(user1, user2);
		
		Users expected = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		expected.setId(1);
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void testUpdateUserBlankInput_FirstName_negative() throws InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user1.setId(1);
		
		Users user2 = new Users("JDoe", "password1", "   ", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			us.updateUser(user1, user2);
		});
	}
	
	@Test
	public void testUpdateUserBlankInput_LastName_negative() throws InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user1.setId(1);
		
		Users user2 = new Users("JDoe", "password1", "John", "  ", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			us.updateUser(user1, user2);
		});
	}
	
	@Test
	public void testUpdateUser_BellowAgeRange_negative() throws InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user1.setId(1);
		
		Users user2 = new Users("JDoe", "password1", "John", "Doe", 4, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			us.updateUser(user1, user2);
		});
	}
	
	@Test
	public void testUpdateUser_AboveAgeRange_negative() throws InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user1.setId(1);
		
		Users user2 = new Users("JDoe", "password1", "John", "Doe", 224, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			us.updateUser(user1, user2);
		});
	}
	
	@Test
	public void testUpdateUserBlankInput_Email_negative() throws InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user1.setId(1);
		
		Users user2 = new Users("JDoe", "password1", "John", "Doe", 24, "  ", "01/1/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			us.updateUser(user1, user2);
		});
	}
	
	@Test
	public void testUpdateUserBlankInput_BirthDay_negative() throws InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user1.setId(1);
		
		Users user2 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "  ", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			us.updateUser(user1, user2);
		});
	}
	
	@Test
	public void testUpdateUserBlankInput_Address_negative() throws InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user1.setId(1);
		
		Users user2 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "  ", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			us.updateUser(user1, user2);
		});
	}
	
	@Test
	public void testGetUserByUsername_positive() throws InvalidParameterException {
		Users user = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user.setId(1);
		
		Mockito.when(ud.getUser("JDoe")).thenReturn(user);
		
		Users actual = us.getUserByUsername("JDoe");
		
		Users expected = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		expected.setId(1);
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetUserByUsername_negative() {
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			us.getUserByUsername(null);
		});
	}
	
}
