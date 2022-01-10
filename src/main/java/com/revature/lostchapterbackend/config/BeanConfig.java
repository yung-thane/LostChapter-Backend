package com.revature.lostchapterbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.revature.lostchapterbackend.utility.ValidateBookUtil;
import com.revature.lostchapterbackend.utility.ValidateCheckoutUtil;
import com.revature.lostchapterbackend.utility.ValidateUtil;


@Configuration
public class BeanConfig {
	
	@Bean
	public ValidateUtil validateUtil() {
		return new ValidateUtil();
	}
	
	@Bean
	public ValidateBookUtil validateBookUtil() {
		return new ValidateBookUtil();
	}
	
	@Bean
	public ValidateCheckoutUtil validateCheckoutUtil() {
		return new ValidateCheckoutUtil();
	}

}
