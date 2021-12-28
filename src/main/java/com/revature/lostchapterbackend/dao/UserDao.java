package com.revature.lostchapterbackend.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.lostchapterbackend.dto.SignUpDto;
import com.revature.lostchapterbackend.model.Users;

@Repository
public class UserDao {
	
	@PersistenceContext
	private EntityManager em;
	
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
		Users user = em.createQuery("FROM Users u WHERE u.username = :username AND u.password = :password", Users.class)
				.setParameter("username", access)
				.setParameter("password", password)
				.getSingleResult();
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

}
