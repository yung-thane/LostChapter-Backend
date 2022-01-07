package com.revature.lostchapterbackend.usertests;

import static org.mockito.Mockito.mock;

import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.revature.lostchapterbackend.dao.UserDao;
import com.revature.lostchapterbackend.dto.SignUpDto;
import com.revature.lostchapterbackend.exceptions.InvalidLoginException;
import com.revature.lostchapterbackend.exceptions.InvalidParameterException;
import com.revature.lostchapterbackend.exceptions.UserNotFoundException;
import com.revature.lostchapterbackend.model.Carts;
import com.revature.lostchapterbackend.model.Users;
import com.revature.lostchapterbackend.service.UserService;
import com.revature.lostchapterbackend.utility.HashUtil;
import com.revature.lostchapterbackend.utility.ValidateUtil;

public class ValidateUtilTest {
	
	@InjectMocks
	@Resource
	private ValidateUtil vu;
	
	@Mock
	private UserService us;

	@Mock
	private UserDao ud;
	
	@BeforeEach
	public void setUp() {
		this.vu = new ValidateUtil();
		
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testCreateUser_positive() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		Users user = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user.setId(1);
		
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		Carts c = null;
		Mockito.when(ud.addUser(createdUser, c)).thenReturn(user);
		Mockito.when(us.getUserByEmail(user.getEmail())).thenReturn(null);
		Mockito.when(us.getUserByUsername(user.getUsername())).thenReturn(null);
		
		vu.verifySignUp(createdUser);
		
	}
	
	@Test
	public void testCreateUserUsernameIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto(" ", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserPasswordIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto("JDoe", " ", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserFirstNameIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", " ", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserLastNameIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", " ", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserEmailIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", 24, " ", "01/1/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserBirthdayIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", " ", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserAddressIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", " ", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserRoleIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", " ");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserInvalidEmail_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", 24, "jdoegmail", "01/1/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserInvalidBirthdayMissingForwardSlash_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail", "0111997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserInvalidBirthdayLessNumbers_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail", "01/1/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserInvalidBirthdayToManyNumbers_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail", "01/01/19978", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserUsernameAndPasswordIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto(" ", " ", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserUsernameAndFirstNameIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto(" ", "password1", " ", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserUsernameAndLastNameIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto(" ", "password1", "John", " ", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserUsernameAndEmailIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto(" ", "password1", "John", "Doe", 24, " ", "01/01/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserUsernameAndBirthdayIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto(" ", "password1", "John", "Doe", 24, "jdoe@gmail.com", " ", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserUsernameAndAddressIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto(" ", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", " ", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserUsernameAndRoleIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto(" ", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", " ");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserUsernamePasswordAndFirstNameIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto(" ", " ", " ", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserUsernamePasswordFirstNameAndLastNameIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto(" ", " ", " ", " ", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserUsernamePasswordFirstNameLastNameAndEmailIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto(" ", " ", " ", " ", 24, " ", "01/01/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserUsernamePasswordFirstNameLastNameEmailAndBirthdayIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto(" ", " ", " ", " ", 24, " ", " ", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserUsernamePasswordFirstNameLastNameEmailBirthdayAndAddressIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto(" ", " ", " ", " ", 24, " ", " ", " ", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserEverythingButAgeIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto(" ", " ", " ", " ", 24, " ", " ", " ", " ");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserUsernameIsNull_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto(null, "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserPasswordIsNull_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto("JDoe", null, "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserFirstNameIsNull_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", null, "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserLastNameIsNull_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", null, 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserEmailIsNull_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", 24, null, "01/01/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserBirthdayIsNull_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", null, "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserAddressIsNull_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", null, "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserRoleIsNull_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", null);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserRoleUsernameIsNullAndEmailIsBlank_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto(null, "password1", "John", "Doe", 24, " ", "01/01/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testCreateUserRoleUsernameIsBlankAndEmailIsNull_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		SignUpDto createdUser = new SignUpDto(" ", "password1", "John", "Doe", 24, null, "01/01/1997", "22nd Ave", "Customer");
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifySignUp(createdUser);
		});
	}
	
	@Test
	public void testUpdateUserByUsername_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JohnD",  "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByPassword_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe",  "password2", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		user2.setPassword(HashUtil.hashPassword("password2", "SHA-256"));
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByFirstName_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe",  "password1", "Jane", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByLastName_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe",  "password1", "John", "Jacob", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByAge_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe",  "password1", "John", "Doe", 25, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByBirthday_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe",  "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/02/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByAddress_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JohnD",  "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "123 River Street", "Customer");
		user2.setId(1);
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByRole_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JohnD",  "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Admin");
		user2.setId(1);
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByUsernameAndPassword_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JohnD",  "password2", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		user2.setPassword(HashUtil.hashPassword("password2", "SHA-256"));
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByUsernameAndFirstName_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JaneD",  "password1", "Jane", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByUsernameAndLastName_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JohnJ",  "password1", "John", "Jacob", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByUsernameAndAge_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JohnD",  "password1", "John", "Doe", 25, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByUsernameAndEmail_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JohnD",  "password1", "John", "Doe", 24, "johnd@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByUsernameAndBirthday_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JohnD",  "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/02/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByUsernameAndAddress_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JohnD",  "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "123 River Street", "Customer");
		user2.setId(1);
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByUsernameAndRole_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JohnD",  "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Admin");
		user2.setId(1);
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByUsernamePasswordAndFirstName_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JaneD",  "password2", "Jane", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		user2.setPassword(HashUtil.hashPassword("password2", "SHA-256"));
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByUsernamePasswordFirstNameAndLastName_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JaneS",  "password2", "Jane", "Smith", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		user2.setPassword(HashUtil.hashPassword("password2", "SHA-256"));
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByUsernamePasswordFirstNameLastNameAndAge_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JaneS",  "password2", "Jane", "Smith", 25, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		user2.setPassword(HashUtil.hashPassword("password2", "SHA-256"));
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByUsernamePasswordFirstNameLastNameAgeAndEmail_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JaneS",  "password2", "Jane", "Smith", 25, "jsmith@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		user2.setPassword(HashUtil.hashPassword("password2", "SHA-256"));
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByUsernamePasswordFirstNameLastNameAgeEmailAndBirthday_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JaneS",  "password2", "Jane", "Smith", 25, "jsmith@gmail.com", "01/02/1997", "22nd Ave", "Customer");
		user2.setId(1);
		user2.setPassword(HashUtil.hashPassword("password2", "SHA-256"));
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByUsernamePasswordFirstNameLastNameAgeEmailBirthdayAndAddress_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JaneS",  "password2", "Jane", "Smith", 25, "jsmith@gmail.com", "01/02/1997", "123 River Street", "Customer");
		user2.setId(1);
		user2.setPassword(HashUtil.hashPassword("password2", "SHA-256"));
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserByAll_positive() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JaneS",  "password2", "Jane", "Smith", 25, "jsmith@gmail.com", "01/02/1997", "22nd Ave", "Admin");
		user2.setId(1);
		user2.setPassword(HashUtil.hashPassword("password2", "SHA-256"));
		
		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
		
		vu.verifyUpdateUser(user2);
		
	}
	
	@Test
	public void testUpdateUserUsernameIsBlank_negative() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users(" ", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserPasswordIsBlank_negative() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe", " ", "John", "Doe", 24, "jdoe@gmail.com", "01/1/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserFirstNameIsBlank_negative() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe", "password1", " ", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserLastNameIsBlank_negative() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe", "password1", "John", " ", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserEmailIsBlank_negative() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe", "password1", "John", "Doe", 24, " ", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserBirthdayIsBlank_negative() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", " ", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserAddressIsBlank_negative() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", " ", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserRoleIsBlank_negative() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", " ");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserInvalidEmail_negative() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoegmail", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserInvalidBirthdayMissingForwardSlash_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail", "0111997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserInvalidBirthdayHasLessNumbers_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail", "01/1/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserInvalidBirthdayToManyNumbers_negative() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail", "01/01/19978", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserUsernameIsNull_negative() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users(null, "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserPasswordIsNull_negative() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe", null, "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserFirstNameIsNull_negative() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe", "password1", null, "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserLastNameIsNull_negative() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe", "password1", "John", null, 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserEmailIsNull_negative() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe", "password1", "John", "Doe", 24, null, "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserBirthdayIsNull_negative() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", null, "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserAddressIsNull_negative() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", null, "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserRoleIsNull_negative() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", null);
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserUsernameIsNullAndEmailIsBlank_negative() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users(null, "password1", "John", "Doe", 24, " ", "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}
	
	@Test
	public void testUpdateUserUsernameIsBlankAndEmailIsNull_negative() throws NoSuchAlgorithmException, InvalidParameterException {
		Users user1 = new Users("JDoe", "password1", "John", "Doe", 24, "jdoe@gmail.com", "01/01/1997", "22nd Ave", "Customer");
		user1.setId(1);
		user1.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
		
		Users user2 = new Users(" ", "password1", "John", "Doe", 24, null, "01/01/1997", "22nd Ave", "Customer");
		user2.setId(1);
		
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			vu.verifyUpdateUser(user2);
		});
	}

}
