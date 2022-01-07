package com.revature.lostchapterbackend.usertests;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

import com.revature.lostchapterbackend.dao.UserDao;
import com.revature.lostchapterbackend.dto.SignUpDto;
import com.revature.lostchapterbackend.model.Carts;
import com.revature.lostchapterbackend.model.Users;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserDaoTest {
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private UserDao sut;
	
	@Test
	@Transactional
	public void testgetUser_positive() {
		
		Users user = new Users("jane_doe", "pass12345", "Jane", "Doe", 23, "jDoe32@gmail.com", "04/23/1998", "232 Lake Sheen Ave", "customer");
		this.em.persist(user);
		
		this.em.flush();
		
		Users actual = this.sut.getUser("jane_doe", "pass12345");
		
		Users expected = new Users("jane_doe", "pass12345", "Jane", "Doe", 23, "jDoe32@gmail.com", "04/23/1998", "232 Lake Sheen Ave", "customer");
		expected.setId(1);
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@Transactional
	
	public void testGetUser_incorrectUsername() {
		
		Users user = new Users("jane_doe", "pass12345", "Jane", "Doe", 23, "jDoe32@gmail.com", "04/23/1998", "232 Lake Sheen Ave", "customer");
		this.em.persist(user);
		
		this.em.flush();
		
		Assertions.assertThrows(DataAccessException.class, () -> {
			this.sut.getUser("wrongusername", "pass12345");
		});
	}
	
	@Test
	@Transactional
	
	public void testGetUser_incorrectPassword() {
		
		Users user = new Users("jane_doe", "pass12345", "Jane", "Doe", 23, "jDoe32@gmail.com", "04/23/1998", "232 Lake Sheen Ave", "customer");
		this.em.persist(user);
		
		this.em.flush();
		
		Assertions.assertThrows(DataAccessException.class, () -> {
			this.sut.getUser("jane_doe", "wrongpassword");
		});
	}
	
	@Test
	@Transactional
	
	public void testGetUser_incorrectUsernameAndPassword() {
		
		Users user = new Users("jane_doe", "pass12345", "Jane", "Doe", 23, "jDoe32@gmail.com", "04/23/1998", "232 Lake Sheen Ave", "customer");
		this.em.persist(user);
		
		this.em.flush();
		
		Assertions.assertThrows(DataAccessException.class, () -> {
			this.sut.getUser("wrongusername", "worngpassword");
		});
	}
	
	@Test
	@Transactional
	
	public void testGetUser_UsernameIsBlank() {
		
		Users user = new Users("jane_doe", "pass12345", "Jane", "Doe", 23, "jDoe32@gmail.com", "04/23/1998", "232 Lake Sheen Ave", "customer");
		this.em.persist(user);
		
		this.em.flush();
		
		Assertions.assertThrows(DataAccessException.class, () -> {
			this.sut.getUser("", "pass12345");
		});
	}
	
	@Test
	@Transactional
	
	public void testGetUser_PasswordIsBlank() {
		
		Users user = new Users("jane_doe", "pass12345", "Jane", "Doe", 23, "jDoe32@gmail.com", "04/23/1998", "232 Lake Sheen Ave", "customer");
		this.em.persist(user);
		
		this.em.flush();
		
		Assertions.assertThrows(DataAccessException.class, () -> {
			this.sut.getUser("jane_doe", "");
		});
	}
	
	@Test
	@Transactional
	
	public void testGetUser_UsernameAndPasswordIsBlank() {
		
		Users user = new Users("jane_doe", "pass12345", "Jane", "Doe", 23, "jDoe32@gmail.com", "04/23/1998", "232 Lake Sheen Ave", "customer");
		this.em.persist(user);
		
		this.em.flush();
		
		Assertions.assertThrows(DataAccessException.class, () -> {
			this.sut.getUser("", "");
		});
	}
	
	@Test
	@Transactional
	
	public void testaddUser_positive() {
		
		Users user = new Users("jane21", "password4", "Jane", "Doe", 23, "jDoe32@gmail.com", "04/23/1998", "232 Lake Sheen Ave", "customer");
		user.setId(1);
		
		Users expected = new Users("jane21", "password4", "Jane", "Doe", 23, "jDoe32@gmail.com", "04/23/1998", "232 Lake Sheen Ave", "customer");
		expected.setId(1);
		
		Assertions.assertEquals(expected, user);
	}
	
	@Test
	@Transactional
	
	public void testAddUser_UsernameAlreadyExist() {
		
		Users user = new Users("jane21", "password4", "Jane", "Doe", 23, "jDoe32@gmail.com", "04/23/1998", "232 Lake Sheen Ave", "customer");
		this.em.persist(user);
		Carts c = null;
		
		SignUpDto newUser = new SignUpDto("jane21", "password4", "Jane", "Doe", 23, "jDoe45@gmail.com", "04/23/1998", "232 Lake Sheen Ave", "customer");
		
		
		this.em.flush();
		
		Assertions.assertThrows(DataAccessException.class, () -> {
			this.sut.addUser(newUser, c);
		});
		
	}
	
	@Test
	@Transactional
	
	public void testAddUser_EmailAlreadyExist() {
		
		Users user = new Users("jane21", "password4", "Jane", "Doe", 23, "jDoe32@gmail.com", "04/23/1998", "232 Lake Sheen Ave", "customer");
		this.em.persist(user);
		Carts c = null;
		
		SignUpDto newUser = new SignUpDto("jane22", "password4", "Jane", "Doe", 23, "jDoe32@gmail.com", "04/23/1998", "232 Lake Sheen Ave", "customer");
		
		
		this.em.flush();
		
		Assertions.assertThrows(DataAccessException.class, () -> {
			this.sut.addUser(newUser, c);
		});
		
	}
	
	@Test
	@Transactional
	
	public void testAddUser_UsernameAndEmailAlreadyExist() {
		
		Users user = new Users("jane21", "password4", "Jane", "Doe", 23, "jDoe32@gmail.com", "04/23/1998", "232 Lake Sheen Ave", "customer");
		this.em.persist(user);
		Carts c = null;
		
		SignUpDto newUser = new SignUpDto("jane21", "password4", "Jane", "Doe", 23, "jDoe32@gmail.com", "04/23/1998", "232 Lake Sheen Ave", "customer");
		
		
		this.em.flush();
		
		Assertions.assertThrows(DataAccessException.class, () -> {
			this.sut.addUser(newUser, c);
		});
		
	}
	
	@Test
	@Transactional
	
	public void testgetUserByEmail_positive() {
		
		Users user = new Users("jane_doe", "pass12345", "Jane", "Doe", 23, "jDoe32@gmail.com", "04/23/1998", "232 Lake Sheen Ave", "customer");
		this.em.persist(user);
		
		this.em.flush();
		
		Users actual = this.sut.getUserByEmail("jDoe32@gmail.com");
		
		Users expected = new Users("jane_doe", "pass12345", "Jane", "Doe", 23, "jDoe32@gmail.com", "04/23/1998", "232 Lake Sheen Ave", "customer");
		expected.setId(1);
		
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@Transactional
	
	public void testgetUserByEmail_BlankEmail() {
		
		Users user = new Users("jane_doe", "pass12345", "Jane", "Doe", 23, "jDoe32@gmail.com", "04/23/1998", "232 Lake Sheen Ave", "customer");
		this.em.persist(user);
		
		this.em.flush();
		
		Assertions.assertEquals(null, sut.getUserByEmail(" "));
	}
	
	

}
