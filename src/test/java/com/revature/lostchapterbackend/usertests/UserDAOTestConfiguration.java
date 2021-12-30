package com.revature.lostchapterbackend.usertests;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.revature.lostchapterbackend.dao.UserDao;

@Profile("UserService-test")
@Configuration
public class UserDAOTestConfiguration {
	@Bean
	@Primary
	public UserDao ud() {
		return Mockito.mock(UserDao.class);
	}
}
