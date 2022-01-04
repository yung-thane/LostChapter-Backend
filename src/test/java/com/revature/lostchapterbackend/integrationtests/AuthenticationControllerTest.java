package com.revature.lostchapterbackend.integrationtests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lostchapterbackend.dto.LoginDto;
import com.revature.lostchapterbackend.model.Users;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.CascadeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        EntityManager em = emf.createEntityManager();
        Session session = em.unwrap(Session.class);
        Transaction tx = session.beginTransaction();

        Users user1 = new Users("test123","5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8","testfn",
                "testln",21,"test123@gmail.com","1990-12-09",
                "address123","customer");
        session.persist(user1);

        tx.commit();

        session.close();
    }

    @Test
    @Transactional
    public void testlogin() throws Exception {



        LoginDto dto = new LoginDto("test123", "password");
        String jsonToSend = mapper.writeValueAsString(dto);
        
        System.out.println(jsonToSend);


        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/login")
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        Users expectedUser = new Users("test123","5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8","testfn",
                "testln",21,"test123@gmail.com","1990-12-09",
                "address123","customer");
        expectedUser.setId(1);

        String expectedJson = mapper.writeValueAsString(expectedUser);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(200)).andExpect(MockMvcResultMatchers.content().json(expectedJson));
    }


}
