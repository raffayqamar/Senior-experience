package com.cs4360msudenver.ueventspringbootbackend.image;

import com.cs4360msudenver.ueventspringbootbackend.User.CustomUserDetailsService;
import com.cs4360msudenver.ueventspringbootbackend.User.JwtUtil;
import com.cs4360msudenver.ueventspringbootbackend.User.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManagerFactory;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ImageController.class)
public class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @MockBean
    private ImageRepository imageRepository;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private ObjectMapper objectMapper;

    @NotNull
    private static Image getImage() throws ParseException {
        Image image = new Image();
        image.setId(95L);
        image.setImage("image");
        User user = new User();
        user.setUsername("someUser");
        image.setUser(user);
        return image;
    }

    @BeforeEach
    public void setup() {
    }

    @Test
    void testGetAllImages() throws Exception {
        Image testImage = getImage();
        // TODO: Using the testImage object, mock the service response when the getAllImages() method is called.
        when(imageService.getAllImages()).thenReturn(Collections.singletonList(testImage));
        mockMvc.perform(MockMvcRequestBuilders.get("/images"))
                /*
                 * TODO-Complete: The MockMvcResultMatchers class provides a set of static methods to perform different types of matchers on the response.
                 *   - The status() method checks the HTTP status code returned by the controller method.
                 *   - The content() method checks the response body.
                 * */
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                // TODO-Done: The jsonPath() method checks the JSON response body using a JSONPath expression and the $.length() expression checks the length of the JSON array returned by the controller method.
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(95L))
                // TODO-Done: The jsonPath() method checks the JSON response body using a JSONPath expression and the $[0].username expression checks the username of the first image in the JSON array returned by the controller method.
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].user.username").value("someUser"));
    }

    @Test
    void testGetImageById() throws Exception {
        Image testImage = getImage();
        // TODO: Using the testImage object, mock the service response when the getAllImages() method is called.
        when(imageService.getImageById(testImage.getId())).thenReturn(testImage);
        mockMvc.perform(MockMvcRequestBuilders.get("/images/" + testImage.getId()))
                // TODO-Complete: Test the mock behavior of the controller and service in handling a request not just the service using the getImageById() method
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(95L)) // Check that the JSON response has ID 95L
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.username").value("someUser")); // Check that the JSON response has username "someUser"

        // TODO-Done: Confirm that the service method was called with the correct image ID
        verify(imageService).getImageById(testImage.getId());
    }

    @Test
    void testSaveImage() throws Exception {
        // Create a mock MultipartFile for a successful upload
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "image data".getBytes()
        );

        // Mock the successful image upload
        doNothing().when(imageService).saveImageToDB(mockFile, "someUser");

        // Perform the image upload
        mockMvc.perform(MockMvcRequestBuilders.multipart("/images/uploadImage/someUser")
                        .file(mockFile))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Expect a 200 OK status
                .andExpect(MockMvcResultMatchers.content().string("Image uploaded successfully"));

        // Verify that the service method was called with the correct file and username
        verify(imageService, times(1)).saveImageToDB(mockFile, "someUser");
    }

    @Test
    public void testSaveImageToDBInternalServerError() throws Exception {

        String testUsername = "someUser";
        // Create a mock MultipartFile for a failed upload
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "image data".getBytes()
        );

        /*
         * TODO-IMPORTANT: Defined a custom checked exception specific for the method and declared it in the method signature, for both the controller and service.
         *  This exception is thrown in my catch block and handled in the test for the service and controller classes.
         *  - This was the alternative since Mockito does not work with void methods and exceptions.
         * */

        // Mock the image upload to throw CustomImageUploadException (simulating failure)
        doThrow(CustomImageUploadException.class).when(imageService).saveImageToDB(mockFile, testUsername);

        // Perform the image upload
        mockMvc.perform(MockMvcRequestBuilders.multipart("/images/uploadImage/" + testUsername)
                        .file(mockFile))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError()) // Expect a 500 Internal Server Error status
                .andExpect(MockMvcResultMatchers.content().string("Failed to upload image"));

        // Verify that the service method was called with the correct file and username
        verify(imageService, times(1)).saveImageToDB(mockFile, testUsername);
    }


    /*
     * Custom Repository Method Test for getImageIdByUsername from ImageRepository interface
     * */
    @Test
    public void testGetImageIdByUsername() throws Exception {
        /*
         * TODO-Done: The parameters for the test are: username and imageId
         *  - username: the username of the image
         *  - imageId: the ID of the image
         * */
        Image testImage = getImage();
        String username = testImage.getUser().getUsername();
        Long imageId = testImage.getId();

        // Mock the service response
        when(imageService.findIdsByUsername(username)).thenReturn(Collections.singletonList(imageId));

        /*
         * TODO-Done: Test the mock behavior of the controller and service in handling a request.
         *  - Check the status of the response (200 OK)
         *  - Check the content type of the response (application/json)
         *  - Check the JSON response body using a JSONPath expression and the $[0] expression checks the ID of the image returned by the controller method.
         * */
        mockMvc.perform(MockMvcRequestBuilders.get("/images/image/" + username))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value(imageId));

        // Verify that the service method was called
        verify(imageService).findIdsByUsername(username);
    }

    //    ADD DELETE WHEN MANAGE EVENTS GET IMPLEMENTED.
    /*
     * TODO-Complete: Test the mock behavior of the controller and service in handling a request.
     *  - This test will test the behavior of the controller and service in handling a request to delete an image.
     * */
    @Test
    public void testDeleteImage() throws Exception {
        Image testImage = getImage();
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/images/deleteImage/95")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        when(imageService.getImageById(testImage.getId())).thenReturn(testImage);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().string(""));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(95L, testImage.getId().longValue());
        assertEquals(HttpStatus.NO_CONTENT.value(),response.getStatus());
    }
}
