package com.revature.lostchapterbackend.integrationtests;

import javax.servlet.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lostchapterbackend.dto.SignUpDto;

import com.revature.lostchapterbackend.utility.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@ContextConfiguration
@WebAppConfiguration
public class AuthenticationControllerTestJwt {

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mvc;

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(webAppContext)
                .addFilters(springSecurityFilterChain)
                .build();
        //jwtUtil = new JWTUtil();
    }

    @Test
    public void testCreateUser_positive() throws Exception {
        SignUpDto dto = new SignUpDto(
                "test1",
                "password",
                "testFirstName",
                "testLastName",
                21,
                "test@gmail.com",
                "09/08/1990",
                "addresswest",
                "Customer");

        String jsonToSend = mapper.writeValueAsString(dto);

        String generatedToken = jwtUtil.generateToken(dto.getUsername());
        Map<String, Object> jsonMap = Collections.singletonMap("jwt-token", generatedToken);
        String expectedJson = mapper.writeValueAsString(jsonMap);

        mvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToSend))
                .andExpect(status().is(201))
                .andExpect(content().json(expectedJson));
    }
}
