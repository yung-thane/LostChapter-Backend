package com.revature.lostchapterbackend.integrationtests;

import javax.servlet.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lostchapterbackend.dto.LoginDto;
import com.revature.lostchapterbackend.dto.SignUpDto;

import com.revature.lostchapterbackend.model.Users;
import com.revature.lostchapterbackend.utility.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
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

    private static String testToken;
    private SignUpDto testDto = new SignUpDto(
            "test",
            "password",
            "testFirstName",
            "testLastName",
            21,
            "test@aol.com",
            "09/08/1990",
            "addresswest",
            "Customer");

    @BeforeEach
    public void setup() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(webAppContext)
                .addFilters(springSecurityFilterChain)
                .build();

        String signUpDtoJson = mapper.writeValueAsString(testDto);
        String response = mvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(signUpDtoJson))
                .andReturn().getResponse().getContentAsString();

        System.out.println(response);
        if (!response.equals("Email already exist."))
            testToken = response.substring(14, response.length() - 2);
        System.out.println(testToken);
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

            mvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToSend))
                    //.andExpect(status().is(200))
                    .andExpect(content().json(expectedToken));
        }

        @Test
        public void testLogin_wrongUsername_negative() throws Exception {
            LoginDto dto = new LoginDto(
                    "tes",
                    "password"
            );

            String jsonToSend = mapper.writeValueAsString(dto);

            mvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToSend))
                    .andExpect(status().is(400))
                    .andExpect(content().string("Username is incorrect."));
        }

        @Test
        public void testLogin_wrongPassword_negative() throws Exception {
            LoginDto dto = new LoginDto(
                    "test",
                    "pass"
            );

            String jsonToSend = mapper.writeValueAsString(dto);

            mvc.perform(post("/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToSend))
                    .andExpect(status().is(400))
                    .andExpect(content().string("Password is incorrect."));
        }

        @Test
        public void testCheckLoginStatus_positive() throws Exception {
            System.out.println(testToken);
            mvc.perform(get("/loginstatus")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + testToken))
                    .andExpect(status().is(200));
        }

        @Test
        public void testCheckLoginStatus_noToken() throws Exception {
            mvc.perform(get("/loginstatus"))
                    .andExpect(status().is(400));
        }

        @Test
        public void testCheckLoginStatus_noUserForToken() throws Exception {
            String fakeToken = jwtUtil.generateToken("fake");

            mvc.perform(get("/loginstatus")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + fakeToken))
                    .andExpect(status().is(400));
        }
    }

    @Nested
    @DisplayName("Delete tests")
    class DeleteTests {
        @Test
        public void testDeleteUser_positive() throws Exception {
            mvc.perform(delete("/user")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + testToken))
                    .andExpect(status().is(200))
                    .andExpect(content().string("This user has been successfully deleted by id: " + 1));
        }

        @Test
        public void testDeleteUser_notLoggedIn_negative() throws Exception {
            mvc.perform(delete("/user"))
                    .andExpect(status().is(400));
        }
    }

    @Nested
    @DisplayName("Update tests")
    class UpdateTests {
        @Test
        public void testUpdateUser_positive() throws Exception {
            Users updateUser = new Users(
                    testDto.getUsername(),
                    testDto.getPassword(),
                    testDto.getFirstName(),
                    testDto.getLastName(),
                    testDto.getAge(),
                    testDto.getEmail(),
                    testDto.getBirthday(),
                    testDto.getAddress(),
                    testDto.getRole());
            updateUser.setId(4);

            String jsonToSend = mapper.writeValueAsString(updateUser);

            updateUser.setId(4);

            String expectedJson = mapper.writeValueAsString(updateUser);
            mvc.perform(put("/user")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + testToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonToSend))
                    .andExpect(status().is(200))
                    .andExpect(content().json(expectedJson));
        }

        @Test
        public void testUpdateUser_usernameIsEmpty_negative() throws Exception {}

        @Test
        public void testUpdateUser_passwordIsEmpty_negative() throws Exception {}

        @Test
        public void testUpdateUser_firstNameIsEmpty_negative() throws Exception {}
        @Test
        public void testUpdateUser_lastNameIsEmpty_negative() throws Exception {}
        @Test
        public void testUpdateUser_ageLessThan5_negative() throws Exception {}
        @Test
        public void testUpdateUser_ageGreaterThan125_negative() throws Exception {}
        @Test
        public void testUpdateUser_emailIsEmpty_negative() throws Exception {}
        @Test
        public void testUpdateUser_addressIsEmpty_negative() throws Exception {}

        @Test
        public void testUpdateUser_birthdayIsEmpty_negative() throws Exception {}

        @Test
        public void testUpdateUser_roleIsEmpty_negative() throws Exception {}
    }

    @Nested
    @DisplayName("Logout Tests")
    class LogoutTests {
        @Test
        public void testLogout_positive() throws Exception {
            mvc.perform(post("/logout")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + testToken))
                    .andExpect(status().is(200))
                    .andExpect(content().string("Successfully logged out"));
        }
    }
}
