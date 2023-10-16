//package com.cs4360msudenver.ueventspringbootbackend.UserService;
//
//import User.User;
//import User.UserRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//
//public class UserServiceTest {
//
//    private UserService userService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        userService = new UserService(userRepository);
//    }
//
//    @Test
//    public void testCreateUser() {
//        // Create a user
//        User user = new User();
//        user.setUsername("user");
//
//        // Mock the userRepository.save() method
//        when(userRepository.save(user)).thenReturn(user);
//
//        // Call the createUser method
//        User createdUser = userService.createUser(user);
//
//        // Verify that userRepository.save() was called with the user
//        verify(userRepository, times(1)).save(user);
//
//
//    }
//
//    @Test
//    public void testGetAllUsers() {
//        // Create a list of users for testing
//        List<User> userList = new ArrayList<>();
//        assertTrue(userList.add(new User("user1"))); // Changed from Assertions.assertTrue
//        userList.add(new User("user2"));
//
//        // Mock the userRepository.findAll() method
//        when(userRepository.findAll()).thenReturn(userList);
//
//        // Call the getAllUsers method
//        Iterable<User> users = userService.getAllUsers();
//
//        // Verify that userRepository.findAll() was called
//        verify(userRepository, times(1)).findAll();
//
//        // Assert that the returned users match the test data
//        assertEquals(2, ((List<User>) users).size());
//    }
//
//    @Test
//    public void testGetUserById() {
//        // Create a user with a known ID
//        Long userId = 1L;
//        User user = new User("user");
//        user.setId(userId);
//
//        // Mock the userRepository.findById() method
//        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//
//        // Call the getUserById method
//        Optional<User> foundUser = userService.getUserById(userId);
//
//        // Verify that userRepository.findById() was called with the ID
//        verify(userRepository, times(1)).findById(userId);
//
//        // Assert that the found user matches the test data
//        assertEquals(userId, foundUser.get().getId());
//    }
//}
