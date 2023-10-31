package com.cs4360msudenver.ueventspringbootbackend.Interests;


import com.cs4360msudenver.ueventspringbootbackend.User.CustomUserDetailsService;
import com.cs4360msudenver.ueventspringbootbackend.User.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = InterestController.class)
public class InterestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InterestService interestService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testGetInterests() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/interests")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Interests testInterest = new Interests();
        testInterest.setId(1L);
        testInterest.setInterest("testInterest1");


        Interests testInterest2 = new Interests();
        testInterest2.setId(2L);
        testInterest2.setInterest("testInterest2");

        Mockito.when(interestService.getInterests()).thenReturn(Arrays.asList(testInterest, testInterest2));
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(1L, testInterest.getId());
        assertEquals(2L, testInterest2.getId());

    }

    @Test
    public void testGetInterestsBadRequest() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/interests")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Interests testInterest = new Interests();
        testInterest.setId(1L);
        testInterest.setInterest("testInterest1");

        Mockito.when(interestService.getInterests()).thenThrow(IllegalArgumentException.class);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        assertTrue(response.getContentAsString().isEmpty());


    }

    @Test
    public void testGetInterest() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/interests/id/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Interests testInterest = new Interests();
        testInterest.setId(1L);
        testInterest.setInterest("testInterest1");

        Mockito.when(interestService.getInterest(Mockito.anyLong())).thenReturn(testInterest);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(1L, testInterest.getId());

    }

    @Test
    public void testGetInterestByName() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/interests/testInterest1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Interests testInterest = new Interests();
        testInterest.setId(1L);
        testInterest.setInterest("testInterest1");

        Mockito.when(interestService.getInterestByName(Mockito.anyString())).thenReturn(testInterest);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("testInterest1", testInterest.getInterest());

    }

    @Test
    public void testGetInterestByNameNotFound() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/interests/testInterest")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Interests testInterest = null;

        Mockito.when(interestService.getInterestByName(Mockito.anyString())).thenReturn(testInterest);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertTrue(response.getContentAsString().isEmpty());

    }

    @Test
    public void testGetInterestByNameBadRequest() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/interests/testInterest")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Interests testInterest = null;

        Mockito.when(interestService.getInterestByName(Mockito.anyString())).thenThrow(IllegalArgumentException.class);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Exception"));

    }




    @Test
    public void testSaveInterest() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/interests")
                .accept(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\":1," +
                        "\"interest\":\"testInterest1\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        Interests testInterest = new Interests();
        testInterest.setId(1L);
        testInterest.setInterest("testInterest1");

        Mockito.when(interestService.saveInterest(Mockito.any())).thenReturn(testInterest);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(1L, testInterest.getId());
        assertEquals("testInterest1", testInterest.getInterest());

    }

    @Test
    public void testSaveInterestBadRequest() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/interests")
                .accept(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\":1," +
                        "\"interest\":\"testInterest1\"" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        Interests testInterest = new Interests();
        testInterest.setId(1L);
        testInterest.setInterest("testInterest1");

        Mockito.when(interestService.saveInterest(Mockito.any())).thenThrow(IllegalArgumentException.class);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Exception"));

    }

    @Test
    public void testDeleteInterest() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/interests/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(interestService.deleteInterest(Mockito.anyLong())).thenReturn(true);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());

    }

    @Test
    public void testDeleteInterestNotFound() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/interests/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(interestService.deleteInterest(Mockito.anyLong())).thenReturn(false);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

    }

    @Test
    public void testDeleteInterestBadRequest() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/interests/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(interestService.deleteInterest(Mockito.anyLong())).thenThrow(IllegalArgumentException.class);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());

    }

}
