package com.revature.lostchapterbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class LostChapterBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LostChapterBackendApplication.class, args);
	}

	@Bean @Autowired
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}