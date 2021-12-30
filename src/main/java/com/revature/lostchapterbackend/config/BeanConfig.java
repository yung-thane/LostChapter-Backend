package com.revature.lostchapterbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.revature.lostchapterbackend.utility.ValidateUtil;


@Configuration
public class BeanConfig {
	
	@Bean
	public ValidateUtil validateUtil() {
		return new ValidateUtil();
	}

}
