package com.cs4360msudenver.ueventspringbootbackend.User;


import com.jayway.jsonpath.JsonPath;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;


import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private JwtUtil jwtUtil;


    @NotNull
    private static User getUser() {
        User testUser = new User();
        testUser.setUsername("testEmail");
        testUser.setFirstName("testFirstName");
        testUser.setLastName("testLastName");
        testUser.setPassword("testPassword");
        testUser.setProvider(ProviderEnum.LOCAL);
        testUser.setRole(User.userRole.USER);
        return testUser;
    }

    @Test
    public void testGetUsers() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        User testUser = getUser();
        User testUser2 = getUser();
        User testUser3 = getUser();

        testUser2.setUsername("testEmail2");
        testUser3.setUsername("testEmail3");

        Mockito.when(userService.getUsers()).thenReturn(List.of(testUser, testUser2, testUser3));
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        List<User> users = userService.getUsers();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("testEmail", users.get(0).getUsername());
        assertEquals("testEmail2", users.get(1).getUsername());
        assertEquals("testEmail3", users.get(2).getUsername());
    }
    @Test
    public void testGetUser() throws Exception {
        // Arrange
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/users/testEmail")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        User testUser = getUser();

        // Mock the behavior of userDetailsService to return a UserDetails object with the correct username
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username("testEmail")
                .password(testUser.getPassword())
                .authorities("USER") // assuming "USER" is a valid role, adjust if necessary
                .build();

        Mockito.when(customUserDetailsService.loadUserByUsername("testEmail")).thenReturn(userDetails);
        Mockito.when(userService.getUserByEmail("testEmail")).thenReturn(testUser);

        // Act
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String responseContent = response.getContentAsString();


        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        // you might want to assert the response content here as well
        assertEquals("testEmail", JsonPath.parse(responseContent).read("$.username"));
        assertEquals("testFirstName", JsonPath.parse(responseContent).read("$.firstName"));
        assertEquals("testLastName", JsonPath.parse(responseContent).read("$.lastName"));
        assertEquals("testPassword", JsonPath.parse(responseContent).read("$.password"));
    }


    @Test
    public void testGetUserNotFound() throws Exception {
        // Arrange
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/users/testUsername")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        User testUser = getUser();

        // Mock the behavior of userDetailsService to return a UserDetails object with the correct username
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username("testUsername")
                .password(testUser.getPassword())
                .authorities("USER") // assuming "USER" is a valid role, adjust if necessary
                .build();

        Mockito.when(customUserDetailsService.loadUserByUsername("testUsername")).thenReturn(userDetails);
        Mockito.when(userService.getUserByEmail("testUsername")).thenReturn(null);

        // Act
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertTrue(response.getContentAsString().isEmpty());
    }

    @Test
    public void testCreateUser() throws Exception {
        // Create a mock request to create a user
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"username\":\"testEmail\"," +
                        "\"firstName\":\"testFirstName\"," +
                        "\"lastName\":\"testLastName\"," +
                        "\"password\":\"testPassword\"," +
                        "\"provider\":\"LOCAL\"," +
                        "\"phoneNumber\":\"0000000000\"," +
                        "\"role\":\"USER\"," +
                        "\"jobDescription\":\"testJobDescription\"," +
                        "\"nickname\":\"testNickname\"," +
                        "\"address\":\"testAddress\"," +
                        "\"postalCode\":\"testPostalCode\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        User testUser = getUser();

        // Mock the behavior of userService to return the testUser object
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(testUser);

        // Act
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String responseContent = response.getContentAsString();
        System.out.println("Response content: " + responseContent);

        // Assert
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("testEmail", JsonPath.parse(responseContent).read("$.username"));
    }

    @Test
    public void testCreateUserBadRequest() throws Exception {
        // Create a mock request to create a user
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"username\":\"testEmail\"," +
                        "\"firstName\":\"testFirstName\"," +
                        "\"lastName\":\"testLastName\"," +
                        "\"password\":\"testPassword\"," +
                        "\"provider\":\"LOCAL\"," +
                        "\"phoneNumber\":\"0000000000\"," +
                        "\"role\":\"USER\"," +
                        "\"jobDescription\":\"testJobDescription\"," +
                        "\"nickname\":\"testNickname\"," +
                        "\"address\":\"testAddress\"," +
                        "\"postalCode\":\"testPostalCode\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        User testUser = getUser();

        // Mock the behavior of userService to return the testUser object
        Mockito.when(userService.createUser(Mockito.any())).thenThrow(IllegalArgumentException.class);

        // Act
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String responseContent = response.getContentAsString();
        System.out.println("Response content: " + responseContent);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Exception"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Arrange
        String username = "testEmail";
        User originalUser = getUser();
        User updatedUser = getUser();
        updatedUser.setFirstName("updatedFirstName");
        updatedUser.setLastName("updatedLastName");

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(originalUser.getPassword())
                .authorities("USER") // assuming "USER" is a valid role, adjust if necessary
                .build();

        Mockito.when(customUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        Mockito.when(userService.getUserByEmail(username)).thenReturn(originalUser);
        Mockito.when(userService.saveUser(Mockito.any())).thenReturn(updatedUser);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/users/" + username)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"updatedFirstName\",\"lastName\":\"updatedLastName\"}")
                .contentType(MediaType.APPLICATION_JSON);

        // Act
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String responseContent = response.getContentAsString();

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("updatedFirstName", JsonPath.parse(responseContent).read("$.firstName"));
        assertEquals("updatedLastName", JsonPath.parse(responseContent).read("$.lastName"));
    }

    @Test
    public void testUpdateUserNotFound() throws Exception {
        // Arrange
        String username = "testEmail";
        User originalUser = getUser();
        User updatedUser = getUser();
        updatedUser.setFirstName("updatedFirstName");
        updatedUser.setLastName("updatedLastName");

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(originalUser.getPassword())
                .authorities("USER") // assuming "USER" is a valid role, adjust if necessary
                .build();

        Mockito.when(customUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        Mockito.when(userService.getUserByEmail(username)).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/users/" + username)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"updatedFirstName\",\"lastName\":\"updatedLastName\"}")
                .contentType(MediaType.APPLICATION_JSON);

        // Act
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertTrue(response.getContentAsString().isEmpty());
    }

    @Test
    public void testUpdateUserBadRequest() throws Exception {
        // Arrange
        String username = "testEmail";
        User originalUser = getUser();
        User updatedUser = getUser();
        updatedUser.setFirstName("updatedFirstName");
        updatedUser.setLastName("updatedLastName");

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(originalUser.getPassword())
                .authorities("USER") // assuming "USER" is a valid role, adjust if necessary
                .build();

        Mockito.when(customUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        Mockito.when(userService.getUserByEmail(username)).thenReturn(originalUser);
        Mockito.when(userService.saveUser(Mockito.any())).thenThrow(IllegalArgumentException.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/users/" + username)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"updatedFirstName\",\"lastName\":\"updatedLastName\"}")
                .contentType(MediaType.APPLICATION_JSON);

        // Act
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Exception"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Arrange
        String username = "testEmail";
        User userToDelete = getUser();

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(userToDelete.getPassword())
                .authorities("USER") // assuming "USER" is a valid role, adjust if necessary
                .build();

        Mockito.when(customUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        Mockito.when(userService.getUserByEmail(username)).thenReturn(userToDelete);
        Mockito.when(userService.deleteUser(userToDelete.getUserId())).thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/users/" + username)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // Act
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }



    @Test
    public void testDeleteUserNotFound() throws Exception {
        // Arrange
        String username = "testEmail";
        User userToDelete = getUser();

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(userToDelete.getPassword())
                .authorities("USER") // assuming "USER" is a valid role, adjust if necessary
                .build();

        Mockito.when(customUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        Mockito.when(userService.getUserByEmail(username)).thenReturn(userToDelete);
        Mockito.when(userService.deleteUser(userToDelete.getUserId())).thenReturn(false);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/users/" + username)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // Act
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void testDeleteUserBadRequest() throws Exception {
        // Arrange
        String username = "testEmail";
        User userToDelete = getUser();

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(userToDelete.getPassword())
                .authorities("USER") // assuming "USER" is a valid role, adjust if necessary
                .build();

        Mockito.when(customUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        Mockito.when(userService.getUserByEmail(username)).thenReturn(userToDelete);
        Mockito.when(userService.deleteUser(userToDelete.getUserId())).thenThrow(IllegalArgumentException.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/users/" + username)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // Act
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

}