////I used copilot and chatgpt to help me write this test file
//
//
//package com.cs4360msudenver.ueventspringbootbackend.User;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import java.util.Collection;
//
//import static com.cs4360msudenver.ueventspringbootbackend.User.ProviderEnum.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserTest {
//
//    @Test
//    void noArgsConstructorTest() {
//        User user = new User();
//        assertNull(user.getUsername());
//        assertNull(user.getProvider());
//    }
//
//    @Test
//    void allArgsConstructorTest() {
//        User user = new User("test@email.com", "John", "Doe", "password", GOOGLE);
//        assertEquals("test@email.com", user.getUsername());
//        assertEquals(GOOGLE, user.getProvider());
//    }
//
//    @Test
//    void getAuthoritiesTest() {
//        User user = new User();
//        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
//        assertEquals(1, authorities.size());
//        assertTrue(authorities.contains(new SimpleGrantedAuthority("USER")));
//    }
//
//    @Test
//    void getUsernameTest() {
//        User user = new User();
//        user.setEmail("test@email.com");
//        assertEquals(user.getEmail(), user.getUsername());
//    }
//}