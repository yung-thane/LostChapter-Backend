package com.revature.lostchapterbackend.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.lostchapterbackend.dto.SignUpDto;
import com.revature.lostchapterbackend.model.Users;

@Repository
public class UserDao {
	
	@PersistenceContext
	private EntityManager em;
	
	private Logger logger = LoggerFactory.getLogger(UserDao.class);
	
	//Sign up method
	@Transactional
	public Users addUser(SignUpDto dto) {
		Users createdUser = new Users(dto.getUsername(), dto.getPassword(), dto.getFirstName(), 
				dto.getLastName(), dto.getAge(), dto.getEmail(), dto.getBirthday(), dto.getAddress(), dto.getRole());
		
		em.persist(createdUser);
		
		return createdUser;
	}
	
	//Login method
	@Transactional
	public Users getUser(String access, String password) {
		logger.info("UserDao.getUser() invoked");
		
		Users user = em.createQuery("FROM Users u WHERE u.username = :username AND u.password = :password", Users.class)
				.setParameter("username", access)
				.setParameter("password", password)
				.getSingleResult();
		//logger.info("User: " + user);
		if (user == null) {
			 user = em.createQuery("FROM Users u WHERE u.email = :email AND u.password = :password", Users.class)
					.setParameter("email", access)
					.setParameter("password", password)
					.getSingleResult();
		}
		return user;
	}
	
	//Delete User method
	@Transactional
	public void deleteUserById(int id) {
		Users user = em.find(Users.class, id);
		
		em.remove(user);
	}

	//Getting a user by email
	@Transactional
	public Users getUserByEmail(String email) {
		logger.info("UserDao.getUserByEmail() invoked");
		
		try {
			Users user = em.createQuery("FROM Users u WHERE u.email = :email AND u.password = :password", Users.class)
					.setParameter("email", email).getSingleResult();
			
			logger.debug("users {}", user);
			
			return user;
		} catch (DataAccessException e) {
			
			e.printStackTrace();
			return null;
		} catch (NoResultException e) {
			return null;
		}
		
	}

}
