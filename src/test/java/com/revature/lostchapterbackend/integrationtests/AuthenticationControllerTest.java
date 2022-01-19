package com.revature.lostchapterbackend.integrationtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lostchapterbackend.dto.LoginDto;
import com.revature.lostchapterbackend.dto.SignUpDto;
import com.revature.lostchapterbackend.model.Carts;
import com.revature.lostchapterbackend.model.Users;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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
                "testln",21,"test123@gmail.com","12/09/1990",
                "address123","customer");
        session.persist(user1);
        Carts c = new Carts(user1);
        session.persist(c);

        tx.commit();

        session.close();
        
    }

    @Test
    public void testLogin_positive() throws Exception {



        LoginDto dto = new LoginDto("test123",
                "password");
        String jsonToSend = mapper.writeValueAsString(dto);
        
        System.out.println(jsonToSend);


        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/login")
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        Users expectedUser = new Users("test123","5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8","testfn",

                "testln",21,"test123@gmail.com","12/09/1990",
                "address123","customer");
        expectedUser.setId(1);

        String expectedJson = mapper.writeValueAsString(expectedUser);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(200)).andExpect(MockMvcResultMatchers.content().json(expectedJson));
    }

    @Test
    public void testLoginWrongUsername_negative() throws Exception {
        LoginDto dto = new LoginDto("gdygdweu","password");
        String jsonToSend = mapper.writeValueAsString(dto);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/login")
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder).
                andExpect(MockMvcResultMatchers.status()
                        .is(400))
                .andExpect(MockMvcResultMatchers.content()
                        .string("Username and/or password is incorrect"));

    }

    @Test
    public void testLoginWrongPassword_negative() throws Exception {
        LoginDto dto = new LoginDto("test123","jgdjwgduwk");
        String jsonToSend = mapper.writeValueAsString(dto);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/login")
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder).
                andExpect(MockMvcResultMatchers.status()
                        .is(400))
                .andExpect(MockMvcResultMatchers.content()
                        .string("Username and/or password is incorrect"));

    }

    @Test
    public void testCreateUser_positive() throws Exception {
        EntityManager em = emf.createEntityManager();

        SignUpDto dto = new SignUpDto("testuser1",
                "password123",
                "testfirstname","testlastname",21,"test@list.com",
                "09/08/1990","addresswest","Customer");
        String jsonToSend = mapper.writeValueAsString(dto);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/signup")
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        Users expectedUser = new Users("testuser1",
                "EF92B778BAFE771E89245B89ECBC08A44A4E166C06659911881F383D4473E94F",
                "testfirstname","testlastname",21,"test@list.com",
                "09/08/1990","addresswest","Customer");
        em.persist(expectedUser);
        expectedUser.setId(2);
        Carts c = new Carts(expectedUser);
        em.persist(c);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(201)).andExpect(MockMvcResultMatchers.content().string("Successfully Sign up"));
    }

    @Test
    public void testCreateUserUsernameIsEmpty_negative() throws Exception {

        SignUpDto dto = new SignUpDto(" ",
                "password123",
                "testfirstname","testlastname",21,"test@list.com",
                "09/08/1990","addresswest","customer");
        String jsonToSend = mapper.writeValueAsString(dto);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/signup")
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("username cannot be blank."));

    }

    @Test
    public void testCreateUserPasswordIsEmpty_negative() throws Exception {

        SignUpDto dto = new SignUpDto("test123",
                " ",
                "testfirstname","testlastname",21,"test@list.com",
                "09/08/1990","addresswest","customer");
        String jsonToSend = mapper.writeValueAsString(dto);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/signup")
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("password cannot be blank."));

    }

    @Test
    public void testCreateUserFirstNameIsEmpty_negative() throws Exception {

        SignUpDto dto = new SignUpDto("test123",
                "password123",
                "","testlastname",21,"test@list.com",
                "09/08/1990","addresswest","customer");
        String jsonToSend = mapper.writeValueAsString(dto);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/signup")
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("firstName cannot be blank."));
    }

    @Test
    public void testCreateUserLastNameIsEmpty_negative() throws Exception {

        SignUpDto dto = new SignUpDto("test123",
                "password123",
                "testfirstname","",21,"test@list.com",
                "09/08/1990","addresswest","customer");
        String jsonToSend = mapper.writeValueAsString(dto);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/signup")
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("lastName cannot be blank."));
    }

    @Test
    public void testCreateUserAgeIsLessThan5_negative() throws Exception {

        SignUpDto dto = new SignUpDto("test1234",
                "password123",
                "testfirstname","testlastname",3,"test@list.com",
                "09/08/1990","addresswest","Customer");
        String jsonToSend = mapper.writeValueAsString(dto);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/signup")
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("Age cannot be less than 5 or greater than 125"));

    }

    @Test
    public void testCreateUserAgeIsGreaterThan125_negative() throws Exception {

        SignUpDto dto = new SignUpDto("test1234",
                "password123",
                "testfirstname","testlastname",130,"test@list.com",
                "09/08/1990","addresswest","Customer");

        String jsonToSend = mapper.writeValueAsString(dto);


        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/signup")
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("Age cannot be less than 5 or greater than 125"));

    }

    @Test
    public void testCreateUserEmailIsEmpty_negative() throws Exception {

        SignUpDto dto = new SignUpDto("test123",
                "password123",
                "testfirstname","testlastname",3,"",
                "09/08/1990","addresswest","customer");
        String jsonToSend = mapper.writeValueAsString(dto);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/signup")
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("email cannot be blank."));
    }

    @Test
    public void testCreateUserBirthdateIsEmpty_negative() throws Exception {

        SignUpDto dto = new SignUpDto("test123",
                "password123",
                "testfirstname","testlastname",3,"test@list.com",
                "","addresswest","customer");
        String jsonToSend = mapper.writeValueAsString(dto);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/signup")
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("birthday cannot be blank."));
    }

    @Test
    public void testCreateUserAddressIsEmpty_negative() throws Exception {

        SignUpDto dto = new SignUpDto("test123",
                "password123",
                "testfirstname","testlastname",3,"test@list.com",
                "09/08/1990","","customer");
        String jsonToSend = mapper.writeValueAsString(dto);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/signup")
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("address cannot be blank."));
    }
    
    @Test
    public void testCreateUserRoleIsEmpty_negative() throws Exception {

        SignUpDto dto = new SignUpDto("test123",
                "password123",
                "testfirstname","testlastname",3,"test@list.com",
                "09/08/1990","addresswest","");
        String jsonToSend = mapper.writeValueAsString(dto);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/signup")
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("role cannot be blank."));
    }

    @Test
    public void testCreateUserEverythingIsEmpty_negative() throws Exception {

        SignUpDto dto = new SignUpDto("",
                "",
                "","",3,"",
                "","","");
        String jsonToSend = mapper.writeValueAsString(dto);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/signup")
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("username password firstName lastName email birthday address role cannot be blank."));
    }

    @Test
    public void testCheckLoginStatus_positive() throws Exception {

        Users fakeUser = new Users("test123",
                "5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8",
                "testfn", "testln",21,"test123@gmail.com","12/09/1990",
                "address123","Customer");
        fakeUser.setId(3);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("currentUser", fakeUser);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/loginstatus").session(session);

        String expectedJson = mapper.writeValueAsString(fakeUser);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(200)).andExpect(MockMvcResultMatchers.content().json(expectedJson));
    }

    @Test
    public void testCheckLoginStatus_negative() throws Exception {

        Users fakeUser = new Users("test123",
                "5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8",
                "testfn", "testln",21,"test123@gmail.com","12/09/1990",
                "address123","Customer");
        fakeUser.setId(3);

        MockHttpSession session = new MockHttpSession();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/loginstatus").session(session);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("No one is currently logged in"));
    }

    @Test
    public void testDeleteUserById_positive() throws Exception {
//        EntityManager em = emf.createEntityManager();
//        Session session = em.unwrap(Session.class);
//        Transaction tx = session.beginTransaction();

        Users u = new Users("test1",
                "5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8",
                "testf", "testl",21,"test989@gmail.com","12/09/1990",
                "addresstest","Customer");
        u.setId(1);
        
        Carts c = new Carts(u);
        c.setCartId(1);
        
        MockHttpSession session1 = new MockHttpSession();

        session1.setAttribute("currentUser", u);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/user").session(session1);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(200)).andExpect(MockMvcResultMatchers.content()
                .string("This user has been successfully deleted by id: "+ 1));
    }

    @Test
    public void testUpdateUser_positive() throws Exception {
        EntityManager em = emf.createEntityManager();
        Session session = em.unwrap(Session.class);
        Transaction tx = session.beginTransaction();

        Users u = new Users("testuser1",
                "EF92B778BAFE771E89245B89ECBC08A44A4E166C06659911881F383D4473E94F",
                "testfirstname","testlastname",21,"test@list.com",
                "09/08/1990","addresswest","Customer");
        em.persist(u);

        tx.commit();

        MockHttpSession session1 = new MockHttpSession();

        session1.setAttribute("currentUser", u);

        String jsonToSend = mapper.writeValueAsString(u);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/user").session(session1)
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        Users expectedUser = new Users("testuser1",
                "EF92B778BAFE771E89245B89ECBC08A44A4E166C06659911881F383D4473E94F",
                "testfirstname","testlastname",21,"test@list.com",
                "09/08/1990","addresswest","Customer");
        expectedUser.setId(2);

        String expectedJson = mapper.writeValueAsString(expectedUser);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(200)).andExpect(MockMvcResultMatchers.content()
                .json(expectedJson));
    }

    @Test
    public void testUpdateUserUsernameIsEmpty_negative() throws Exception {
        EntityManager em = emf.createEntityManager();
        Session session = em.unwrap(Session.class);
        Transaction tx = session.beginTransaction();

        Users u = new Users("",
                "EF92B778BAFE771E89245B89ECBC08A44A4E166C06659911881F383D4473E94F",
                "testfirstname","testlastname",21,"test@list.com",
                "09/08/1990","addresswest","Customer");
        em.persist(u);

        tx.commit();

        MockHttpSession session1 = new MockHttpSession();

        session1.setAttribute("currentUser", u);

        String jsonToSend = mapper.writeValueAsString(u);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/user").session(session1)
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

//        Users expectedUser = new Users("testuser1",
//                "EF92B778BAFE771E89245B89ECBC08A44A4E166C06659911881F383D4473E94F",
//                "testfirstname","testlastname",21,"test@list.com",
//                "09/08/1990","addresswest","Customer");
//        expectedUser.setId(2);
//
//        String expectedJson = mapper.writeValueAsString(expectedUser);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("username cannot be blank."));
    }

    @Test
    public void testUpdateUserPasswordIsEmpty_negative() throws Exception {
        EntityManager em = emf.createEntityManager();
        Session session = em.unwrap(Session.class);
        Transaction tx = session.beginTransaction();

        Users u = new Users("testuser1",
                "",
                "testfirstname","testlastname",21,"test@list.com",
                "09/08/1990","addresswest","Customer");
        em.persist(u);

        tx.commit();

        MockHttpSession session1 = new MockHttpSession();

        session1.setAttribute("currentUser", u);

        String jsonToSend = mapper.writeValueAsString(u);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/user").session(session1)
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("password cannot be blank."));
    }

    @Test
    public void testUpdateUserFirstNameIsEmpty_negative() throws Exception {
        EntityManager em = emf.createEntityManager();
        Session session = em.unwrap(Session.class);
        Transaction tx = session.beginTransaction();

        Users u = new Users("testuser1",
                "EF92B778BAFE771E89245B89ECBC08A44A4E166C06659911881F383D4473E94F",
                "","testlastname",21,"test@list.com",
                "09/08/1990","addresswest","Customer");
        em.persist(u);

        tx.commit();

        MockHttpSession session1 = new MockHttpSession();

        session1.setAttribute("currentUser", u);

        String jsonToSend = mapper.writeValueAsString(u);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/user").session(session1)
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);


        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("firstName cannot be blank."));
    }

    @Test
    public void testUpdateUserLastNameIsEmpty_negative() throws Exception {
        EntityManager em = emf.createEntityManager();
        Session session = em.unwrap(Session.class);
        Transaction tx = session.beginTransaction();

        Users u = new Users("testuser1",
                "EF92B778BAFE771E89245B89ECBC08A44A4E166C06659911881F383D4473E94F",
                "testfirstname","",21,"test@list.com",
                "09/08/1990","addresswest","Customer");
        em.persist(u);

        tx.commit();

        MockHttpSession session1 = new MockHttpSession();

        session1.setAttribute("currentUser", u);

        String jsonToSend = mapper.writeValueAsString(u);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/user").session(session1)
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("lastName cannot be blank."));
    }

    @Test
    public void testUpdateAgeLessThan5_negative() throws Exception {
        EntityManager em = emf.createEntityManager();
        Session session = em.unwrap(Session.class);
        Transaction tx = session.beginTransaction();

        Users u = new Users("testuser1",
                "EF92B778BAFE771E89245B89ECBC08A44A4E166C06659911881F383D4473E94F",
                "testfirstname","testlastname",2,"test@list.com",
                "09/08/1990","addresswest","Customer");
        em.persist(u);

        tx.commit();

        MockHttpSession session1 = new MockHttpSession();

        session1.setAttribute("currentUser", u);

        String jsonToSend = mapper.writeValueAsString(u);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/user").session(session1)
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("Age cannot be less than 5 or greater than 125"));
    }

    @Test
    public void testUpdateAgeGreaterThan125_negative() throws Exception {
        EntityManager em = emf.createEntityManager();
        Session session = em.unwrap(Session.class);
        Transaction tx = session.beginTransaction();

        Users u = new Users("testuser1",
                "EF92B778BAFE771E89245B89ECBC08A44A4E166C06659911881F383D4473E94F",
                "testfirstname","testlastname",220,"test@list.com",
                "09/08/1990","addresswest","Customer");
        em.persist(u);

        tx.commit();

        MockHttpSession session1 = new MockHttpSession();

        session1.setAttribute("currentUser", u);

        String jsonToSend = mapper.writeValueAsString(u);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/user").session(session1)
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("Age cannot be less than 5 or greater than 125"));
    }

    @Test
    public void testUpdateUserEmailIsEmpty_negative() throws Exception {
        EntityManager em = emf.createEntityManager();
        Session session = em.unwrap(Session.class);
        Transaction tx = session.beginTransaction();

        Users u = new Users("testuser1",
                "EF92B778BAFE771E89245B89ECBC08A44A4E166C06659911881F383D4473E94F",
                "testfirstname","testlastname",21,"",
                "09/08/1990","addresswest","Customer");
        em.persist(u);

        tx.commit();

        MockHttpSession session1 = new MockHttpSession();

        session1.setAttribute("currentUser", u);

        String jsonToSend = mapper.writeValueAsString(u);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/user").session(session1)
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);


        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("email cannot be blank."));
    }

    @Test
    public void testUpdateUserAddressIsEmpty_negative() throws Exception {
        EntityManager em = emf.createEntityManager();
        Session session = em.unwrap(Session.class);
        Transaction tx = session.beginTransaction();

        Users u = new Users("testuser1",
                "EF92B778BAFE771E89245B89ECBC08A44A4E166C06659911881F383D4473E94F",
                "testfirstname","testlastname",21,"test@list.com",
                "09/08/1990","","Customer");
        em.persist(u);

        tx.commit();

        MockHttpSession session1 = new MockHttpSession();

        session1.setAttribute("currentUser", u);

        String jsonToSend = mapper.writeValueAsString(u);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/user").session(session1)
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("address cannot be blank."));
    }

    @Test
    public void testUpdateUserBirthdayIsEmpty_negative() throws Exception {
        EntityManager em = emf.createEntityManager();
        Session session = em.unwrap(Session.class);
        Transaction tx = session.beginTransaction();

        Users u = new Users("testuser1",
                "EF92B778BAFE771E89245B89ECBC08A44A4E166C06659911881F383D4473E94F",
                "testfirstname","testlastname",21,"test@list.com",
                "","addresswest","Customer");
        em.persist(u);

        tx.commit();

        MockHttpSession session1 = new MockHttpSession();

        session1.setAttribute("currentUser", u);

        String jsonToSend = mapper.writeValueAsString(u);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/user").session(session1)
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("birthday cannot be blank."));
    }

    @Test
    public void testUpdateUserRoleIsEmpty_negative() throws Exception {
        EntityManager em = emf.createEntityManager();
        Session session = em.unwrap(Session.class);
        Transaction tx = session.beginTransaction();

        Users u = new Users("testuser1",
                "EF92B778BAFE771E89245B89ECBC08A44A4E166C06659911881F383D4473E94F",
                "testfirstname","testlastname",21,"test@list.com",
                "09/08/1990","addresswest","");
        em.persist(u);

        tx.commit();

        MockHttpSession session1 = new MockHttpSession();

        session1.setAttribute("currentUser", u);

        String jsonToSend = mapper.writeValueAsString(u);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/user").session(session1)
                .content(jsonToSend).contentType(MediaType.APPLICATION_JSON);


        this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status()
                .is(400)).andExpect(MockMvcResultMatchers.content()
                .string("role cannot be blank."));
    }
    
    @Test
    public void logout_positive() throws Exception {
    	
    	MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/logout");
    	this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(200)).andExpect(MockMvcResultMatchers.content().string("Successfully logged out"));
    	
    }
    
    @Test
    public void delete_user_but_not_logged_in_negative() throws Exception {
    	
    	MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/user");
    	this.mvc.perform(builder).andExpect(MockMvcResultMatchers.status().is(400)).andExpect(MockMvcResultMatchers.content().string("This user does not exist or is not logged in"));
    	
    }

}
