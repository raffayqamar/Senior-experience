package com.cs4360msudenver.ueventspringbootbackend.User;

import com.cs4360msudenver.ueventspringbootbackend.Event.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserService.class)
public class UserServiceTest {

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup() {
        userService.entityManager = entityManager;
    }

    @Test
    public void testGetUsers() {
        Long userId = 1L;
        User expectedUser = new User();
        expectedUser.setUserId(userId);
        expectedUser.setFirstName("testFirstName");
        expectedUser.setLastName("testLastName");

        when(userRepository.findAll()).thenReturn(List.of(expectedUser));

        List<User> users = userService.getUsers();
        assertEquals(1, users.size());
        assertEquals("testFirstName", users.get(0).getFirstName());
    }

    @Test
    public void testGetUser() {
        Long userId = 1L;
        User expectedUser = new User();
        expectedUser.setUserId(userId);
        expectedUser.setFirstName("testFirstName");
        expectedUser.setLastName("testLastName");

        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        User actualUser = userService.getUser(userId);

        assertNotNull(actualUser);
        assertEquals(expectedUser.getUserId(), actualUser.getUserId());
        assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
        assertEquals(expectedUser.getLastName(), actualUser.getLastName());
    }


    @Test
    public void testGetUserNotFound() {
        Long userId = 1L;
        User expectedUser = new User();
        expectedUser.setUserId(userId);
        expectedUser.setFirstName("testFirstName");
        expectedUser.setLastName("testLastName");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        User actualUser = userService.getUser(userId);

        assertNull(actualUser);
    }

    @Test
    public void testSaveUser() {
        Long userId = 1L;
        User expectedUser = new User();
        expectedUser.setUserId(userId);
        expectedUser.setFirstName("testFirstName");
        expectedUser.setLastName("testLastName");

        when(userRepository.saveAndFlush(Mockito.any())).thenReturn(expectedUser);
        when(userRepository.save(Mockito.any())).thenReturn(expectedUser);

        assertEquals(expectedUser, userService.saveUser(expectedUser));
    }

    @Test
    public void testSaveUserError() {
        Long userId = 1L;
        User expectedUser = new User();
        expectedUser.setUserId(userId);
        expectedUser.setFirstName("testFirstName");
        expectedUser.setLastName("testLastName");

        when(userRepository.save(Mockito.any())).thenThrow(IllegalArgumentException.class);
        when(userRepository.saveAndFlush(Mockito.any())).thenThrow(IllegalArgumentException.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.saveUser(expectedUser);
        });

        assertNull(exception.getMessage());
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;
        User expectedUser = new User();
        expectedUser.setUserId(userId);
        expectedUser.setFirstName("testFirstName");
        expectedUser.setLastName("testLastName");


        when(userRepository.existsById(userId)).thenReturn(true);
        doNothing().when(userRepository).deleteById(userId);

        boolean isDeleted = userService.deleteUser(userId);

        assertTrue(isDeleted);
        verify(userRepository).deleteById(userId);
    }

    @Test
    public void testDeleteUserNotFound() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(userRepository.existsById(Mockito.any())).thenReturn(false);
        Mockito.doThrow(IllegalArgumentException.class)
                .when(userRepository)
                .deleteById(Mockito.any());

        assertFalse(userService.deleteUser(1L));
    }
}
