//package com.cs4360msudenver.ueventspringbootbackend.User;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.web.servlet.client.TestRestTemplate;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//
//class UserControllerTest {
//    @localServerPort
//    private int port;
//    @Autowired
//    private TestRestTemplate restTemplate;
//    @MockBean
//    private UserService userService;
//
//    @Test
//    void getUsersTest() {
//        when(userService.getUsers()).thenReturn(null);
//        ResponseEntity<String> response = restTemplate.getForEntity
//                ("http://localhost:" + port + "/api/users", String.class);
//        assertEquals(200, response.getStatusCodeValue());
//
//    }
//
//    @Test
//    void getUserByEmailTest() {
//        String email = "test@email.com";
//        when(userService.getUser(email)).thenReturn(new User(email, "John", "Doe", "password", null));
//
//        ResponseEntity<User> response = restTemplate.getForEntity("http://localhost:" + port + "/api/users/" + email, User.class);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals(email, response.getBody().getEmail());
//    }
//
//
//}



