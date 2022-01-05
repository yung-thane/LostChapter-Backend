package com.revature.lostchapterbackend.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.catalina.User;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.lostchapterbackend.dto.SignUpDto;
import com.revature.lostchapterbackend.model.Carts;
import com.revature.lostchapterbackend.model.Users;

@Repository
public class UserDao {

	@PersistenceContext
	private EntityManager em;

	private Logger logger = LoggerFactory.getLogger(UserDao.class);

	// Sign up method
	@Transactional
	public Users addUser(SignUpDto dto, Carts c) {
		Users createdUser = new Users(dto.getUsername(), dto.getPassword(), dto.getFirstName(), dto.getLastName(),
				dto.getAge(), dto.getEmail(), dto.getBirthday(), dto.getAddress(), dto.getRole());

		em.persist(createdUser);
		
		c = new Carts(createdUser);
		em.persist(c);
		return createdUser;
	}

	// Login method
	@Transactional
	public Users getUser(String access, String password) {
		logger.info("UserDao.getUser() invoked");

		Users user = em.createQuery("FROM Users u WHERE u.username = :username AND u.password = :password", Users.class)
				.setParameter("username", access).setParameter("password", password).getSingleResult();
		logger.info("user {}", user);
		// logger.info("User: " + user);
//		if (user == null) {
//			 user = em.createQuery("FROM Users u WHERE u.email = :email AND u.password = :password", Users.class)
//					.setParameter("email", access)
//					.setParameter("password", password)
//					.getSingleResult();
//		}
		return user;
	}

	@Transactional
	public Users getUser(String access) {
		logger.info("UserDao.getUser() invoked");

		try {
			Users user = em.createQuery("FROM Users u WHERE u.username = :username", Users.class)
					.setParameter("username", access).getSingleResult();
			logger.info("user{}", user);
			return user;
		} catch (NoResultException e) {
			return null;
		}
	}

	// Delete User method
	@Transactional
	public void deleteUserById(int id) {
		Users user = em.find(Users.class, id);

		em.remove(user);
	}

	// Getting a user by email
	@Transactional
	public Users getUserByEmail(String email) {
		logger.info("UserDao.getUserByEmail() invoked");

		try {
			Users user = em.createQuery("FROM Users u WHERE u.email = :email", Users.class).setParameter("email", email)
					.getSingleResult();

			return user;
		} catch (DataAccessException e) {

<<<<<<< HEAD
			e.printStackTrace();
			throw new NoResultException("Email cannot be blank");
		} 
=======
            e.printStackTrace();
            return null;
        } catch (NoResultException e) {
            return null;
        }
>>>>>>> main

	}

	// Updating a user's information
	@Transactional
	public Users updateUser(int id, Users updatedUserInfo) {
		Session session = em.unwrap(Session.class);

		Users currentlyLoggedInUser = session.find(Users.class, id);
		currentlyLoggedInUser = updatedUserInfo;

		session.merge(currentlyLoggedInUser);

		return currentlyLoggedInUser;
	}

}
