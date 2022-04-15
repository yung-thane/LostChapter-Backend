package com.revature.lostchapterbackend.integrationtests;

import javax.servlet.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lostchapterbackend.dto.LoginDto;
import com.revature.lostchapterbackend.dto.SignUpDto;

import com.revature.lostchapterbackend.utility.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    private String testToken;

    @BeforeEach
    public void setup() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(webAppContext)
                .addFilters(springSecurityFilterChain)
                .build();

        SignUpDto dto = new SignUpDto(
                "test",
                "password",
                "testFirstName",
                "testLastName",
                21,
                "test@aol.com",
                "09/08/1990",
                "addresswest",
                "Customer");
        String dtoAsString = mapper.writeValueAsString(dto);

        testToken = mvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoAsString))
                // extract the returned token
                .andReturn().getResponse().getContentAsString();
    }

    @Nested
    @DisplayName("Sign up Tests")
    class SignUpTests {
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

        @Test
        public void testCreateUser_usernameIsEmpty_negative() throws Exception {
            SignUpDto dto = new SignUpDto(
                    " ",
                    "password123",
                    "testfirstname",
                    "testlastname",
                    21,
                    "test@list.com",
                    "09/08/1990",
                    "addresswest",
                    "customer"
            );

            String jsonToSend = mapper.writeValueAsString(dto);

            mvc.perform(post("/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToSend))
                    .andExpect(status().is(400))
                    .andExpect(content().string("Username cannot be blank."));
        }

        @Test
        public void testCreateUser_passwordIsEmpty_negative() throws Exception {
            SignUpDto dto = new SignUpDto(
                    "testuser",
                    " ",
                    "testfirstname",
                    "testlastname",
                    21,
                    "test@list.com",
                    "09/08/1990",
                    "addresswest",
                    "customer"
            );

            String jsonToSend = mapper.writeValueAsString(dto);

            mvc.perform(post("/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToSend))
                    .andExpect(status().is(400))
                    .andExpect(content().string("Password cannot be blank."));
        }

        @Test
        public void testCreateUser_firstNameIsEmpty_negative() throws Exception {
            SignUpDto dto = new SignUpDto(
                    "testuser",
                    "password123",
                    " ",
                    "testlastname",
                    21,
                    "test@list.com",
                    "09/08/1990",
                    "addresswest",
                    "customer"
            );

            String jsonToSend = mapper.writeValueAsString(dto);

            mvc.perform(post("/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToSend))
                    .andExpect(status().is(400))
                    .andExpect(content().string("First name cannot be blank."));
        }

        @Test
        public void testCreateUser_lastNameIsEmpty_negative() throws Exception {
            SignUpDto dto = new SignUpDto(
                    "testuser",
                    "password123",
                    "testfirstname",
                    " ",
                    21,
                    "test@list.com",
                    "09/08/1990",
                    "addresswest",
                    "customer"
            );

            String jsonToSend = mapper.writeValueAsString(dto);

            mvc.perform(post("/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToSend))
                    .andExpect(status().is(400))
                    .andExpect(content().string("Last name cannot be blank."));
        }

        @Test
        public void testCreateUser_ageIsLessThan5_negative() throws Exception {
            SignUpDto dto = new SignUpDto(
                    "testuser",
                    "password123",
                    "testfirstname",
                    "testlastname",
                    3,
                    "test@list.com",
                    "09/08/1990",
                    "addresswest",
                    "customer"
            );

            String jsonToSend = mapper.writeValueAsString(dto);

            mvc.perform(post("/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToSend))
                    .andExpect(status().is(400))
                    .andExpect(content().string("Age cannot be less than 5 or greater than 125."));
        }

        @Test
        public void testCreateUser_ageIsGreaterThan125_negative() throws Exception {
            SignUpDto dto = new SignUpDto(
                    "testuser",
                    "password123",
                    "testfirstname",
                    "testlastname",
                    128,
                    "test@list.com",
                    "09/08/1990",
                    "addresswest",
                    "customer"
            );

            String jsonToSend = mapper.writeValueAsString(dto);

            mvc.perform(post("/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToSend))
                    .andExpect(status().is(400))
                    .andExpect(content().string("Age cannot be less than 5 or greater than 125."));
        }

        @Test
        public void testCreateUser_emailIsEmpty_negative() throws Exception {
            SignUpDto dto = new SignUpDto(
                    "testuser",
                    "password123",
                    "testfirstname",
                    "testlastname",
                    21,
                    " ",
                    "09/08/1990",
                    "addresswest",
                    "customer"
            );

            String jsonToSend = mapper.writeValueAsString(dto);

            mvc.perform(post("/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToSend))
                    .andExpect(status().is(400))
                    .andExpect(content().string("Email cannot be blank."));
        }

        @Test
        public void testCreateUser_birthdayIsEmpty_negative() throws Exception {
            SignUpDto dto = new SignUpDto(
                    "testuser",
                    "password123",
                    "testfirstname",
                    "testlastname",
                    21,
                    "test@list.com",
                    " ",
                    "addresswest",
                    "customer"
            );

            String jsonToSend = mapper.writeValueAsString(dto);

            mvc.perform(post("/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToSend))
                    .andExpect(status().is(400))
                    .andExpect(content().string("Birthday cannot be blank."));
        }

        @Test
        public void testCreateUser_addressIsEmpty_negative() throws Exception {
            SignUpDto dto = new SignUpDto(
                    "testuser",
                    "password123",
                    "testfirstname",
                    "testlastname",
                    21,
                    "test@list.com",
                    "09/08/1990",
                    " ",
                    "customer"
            );

            String jsonToSend = mapper.writeValueAsString(dto);

            mvc.perform(post("/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToSend))
                    .andExpect(status().is(400))
                    .andExpect(content().string("Address cannot be blank."));
        }

        @Test
        public void testCreateUser_roleIsEmpty_negative() throws Exception {
            SignUpDto dto = new SignUpDto(
                    "testuser",
                    "password123",
                    "testfirstname",
                    "testlastname",
                    21,
                    "test@list.com",
                    "09/08/1990",
                    "addresswest",
                    " "
            );

            String jsonToSend = mapper.writeValueAsString(dto);

            mvc.perform(post("/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToSend))
                    .andExpect(status().is(400))
                    .andExpect(content().string("Role cannot be blank."));
        }

        @Test
        public void testCreateUser_everythingIsEmpty_negative() throws Exception {
            SignUpDto dto = new SignUpDto(
                    " ",
                    " ",
                    " ",
                    " ",
                    3,
                    " ",
                    " ",
                    " ",
                    " "
            );

            String jsonToSend = mapper.writeValueAsString(dto);

            mvc.perform(post("/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToSend))
                    .andExpect(status().is(400))
                    .andExpect(content().string("Username Password First name Last name Email Birthday Address Role cannot be blank."));
        }
    }

    @Nested
    @DisplayName("Login and login status Tests")
    class LoginTests {
        @Test
        public void testLogin_positive() throws Exception {
            LoginDto dto = new LoginDto(
                    "test",
                    "password"
            );
            String jsonToSend = mapper.writeValueAsString(dto);

            String token = jwtUtil.generateToken(dto.getUsername());
            String expectedToken = mapper.writeValueAsString(Collections.singletonMap("jwt-token", token));

            System.out.println(mvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToSend))
                    //.andExpect(status().is(200))
                    .andExpect(content().json(expectedToken))
                    .andReturn().getResponse().getContentAsString());
        }
    }
}
