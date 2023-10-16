package com.cs4360msudenver.ueventspringbootbackend.Event;

import com.cs4360msudenver.ueventspringbootbackend.User.CustomUserDetailsService;
import org.jetbrains.annotations.NotNull;
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

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;


    @NotNull
    private static Event getEvent() throws ParseException {
        Event testEvent = new Event();
        testEvent.setEventName("testEvent");
        testEvent.setEmail("testEmail");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse("2021-09-27");
        testEvent.setEventDate(date);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss"); // Use the appropriate time format
        Time time = new Time(timeFormat.parse("12:00:00").getTime()); // Replace "12:00:00" with the actual time string
        testEvent.setEventTime(time);
        testEvent.setLocation("testLocation");
        testEvent.setPostalCode("testPostalCode");
        testEvent.setCity("testCity");
        testEvent.setCountryCode("testCountryCode");
        testEvent.setDescription("testDescription");
        testEvent.setCategory("testCategory");
        testEvent.setImage("testImage");
        testEvent.setImageFile(null);
        String[] tagsArray = {"testTag1", "testTag2"};
        List<String> tags = Arrays.asList(tagsArray);
        testEvent.setTags(tags);
        return testEvent;
    }

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testGetEvents() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/events/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Event testEvent = getEvent();

        Mockito.when(eventService.getEvents()).thenReturn(List.of(testEvent));
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        List<Event> events = eventService.getEvents();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("testEvent", events
                .get(0)
                .getEventName());
    }

    @Test
    public void testGetEvent() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/events/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Event testEvent = getEvent();

        Mockito.when(eventService.getEvent(1L)).thenReturn(testEvent);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"eventId\":null,\"eventName\":\"testEvent\",\"user\":null,\"eventDate\":\"2021-09-27T06:00:00.000+00:00\",\"eventTime\":\"12:00:00\",\"location\":\"testLocation\",\"postalCode\":\"testPostalCode\",\"city\":\"testCity\",\"countryCode\":\"testCountryCode\",\"description\":\"testDescription\",\"category\":\"testCategory\",\"image\":\"testImage\",\"imageFile\":null,\"tags\":[\"testTag1\",\"testTag2\"]}", response.getContentAsString());
    }

    @Test
    public void testGetEventNotFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/events/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(eventService.getEvent(1L)).thenReturn(null);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    public void testCreateEvent() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/events/")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"eventName\": \"testEvent\",\n" +
                        "    \"email\": \"testEmail\",\n" +
                        "    \"eventDate\": \"2021-09-27\",\n" +
                        "    \"eventTime\": \"12:00:00\",\n" +
                        "    \"location\": \"testLocation\",\n" +
                        "    \"postalCode\": \"testPostalCode\",\n" +
                        "    \"city\": \"testCity\",\n" +
                        "    \"countryCode\": \"testCountryCode\",\n" +
                        "    \"description\": \"testDescription\",\n" +
                        "    \"category\": \"testCategory\",\n" +
                        "    \"image\": \"testImage\",\n" +
                        "    \"imageFile\": null,\n" +
                        "    \"tags\": [\"testTag1\", \"testTag2\"]\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        Event testEvent = getEvent();

        Mockito.when(eventService.saveEvent(Mockito.any())).thenReturn(testEvent);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("{\"eventId\":null,\"eventName\":\"testEvent\",\"user\":null,\"eventDate\":\"2021-09-27T06:00:00.000+00:00\",\"eventTime\":\"12:00:00\",\"location\":\"testLocation\",\"postalCode\":\"testPostalCode\",\"city\":\"testCity\",\"countryCode\":\"testCountryCode\",\"description\":\"testDescription\",\"category\":\"testCategory\",\"image\":\"testImage\",\"imageFile\":null,\"tags\":[\"testTag1\",\"testTag2\"]}", response.getContentAsString());
    }


    @Test
    public void testCreateEventBadRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/events/")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"eventName\": \"testEvent\",\n" +
                        "    \"email\": \"testEmail\",\n" +
                        "    \"eventDate\": \"2021-09-27\",\n" +
                        "    \"eventTime\": \"12:00:00\",\n" +
                        "    \"location\": \"testLocation\",\n" +
                        "    \"postalCode\": \"testPostalCode\",\n" +
                        "    \"city\": \"testCity\",\n" +
                        "    \"countryCode\": \"testCountryCode\",\n" +
                        "    \"description\": \"testDescription\",\n" +
                        "    \"category\": \"testCategory\",\n" +
                        "    \"image\": \"testImage\",\n" +
                        "    \"imageFile\": null,\n" +
                        "    \"tags\": [\"testTag1\", \"testTag2\"]\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(eventService.saveEvent(Mockito.any())).thenThrow(IllegalArgumentException.class);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Exception"));
    }


    @Test
    public void testUpdateEvent() throws Exception {
        // Build the request
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/events/1")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"eventName\": \"testEvent\",\n" +
                        "    \"email\": \"testEmail\",\n" +
                        "    \"eventDate\": \"2021-09-27\",\n" +
                        "    \"eventTime\": \"12:00:00\",\n" +
                        "    \"location\": \"testLocation\",\n" +
                        "    \"postalCode\": \"testPostalCode\",\n" +
                        "    \"city\": \"testCity\",\n" +
                        "    \"countryCode\": \"testCountryCode\",\n" +
                        "    \"description\": \"testDescription\",\n" +
                        "    \"category\": \"testCategory\",\n" +
                        "    \"image\": \"testImage\",\n" +
                        "    \"imageFile\": null,\n" +
                        "    \"tags\": [\"testTag1\", \"testTag2\"]\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        // Create a testEvent object
        Event testEvent = getEvent();

        // Stub the getEvent and saveEvent methods of eventService
        Mockito.when(eventService.getEvent(1L)).thenReturn(testEvent);

        Event updatedTestEvent = getEvent();
        updatedTestEvent.setEventName("testEventUpdated");
        Mockito.when(eventService.saveEvent(Mockito.any(Event.class))).thenReturn(updatedTestEvent);

        // Perform the request
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        // Get the response
        MockHttpServletResponse response = result.getResponse();

        // Assert the response status and content
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"eventId\":null,\"eventName\":\"testEventUpdated\",\"user\":null,\"eventDate\":\"2021-09-27T06:00:00.000+00:00\",\"eventTime\":\"12:00:00\",\"location\":\"testLocation\",\"postalCode\":\"testPostalCode\",\"city\":\"testCity\",\"countryCode\":\"testCountryCode\",\"description\":\"testDescription\",\"category\":\"testCategory\",\"image\":\"testImage\",\"imageFile\":null,\"tags\":[\"testTag1\",\"testTag2\"]}", response.getContentAsString());
    }

    @Test
    public void testUpdateEventNotFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/events/1")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"eventName\": \"testEvent\",\n" +
                        "    \"email\": \"testEmail\",\n" +
                        "    \"eventDate\": \"2021-09-27\",\n" +
                        "    \"eventTime\": \"12:00:00\",\n" +
                        "    \"location\": \"testLocation\",\n" +
                        "    \"postalCode\": \"testPostalCode\",\n" +
                        "    \"countryCode\": \"testCountry\",\n" +
                        "    \"description\": \"testDescription\",\n" +
                        "    \"category\": \"testCategory\",\n" +
                        "    \"image\": \"testImage\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(eventService.getEvent(Mockito.any())).thenReturn(null);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertTrue(response.getContentAsString().isEmpty());
    }

    @Test
    public void testUpdateEventBadRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/events/1")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"eventName\": \"testEvent\",\n" +
                        "    \"email\": \"testEmail\",\n" +
                        "    \"eventDate\": \"2021-09-27\",\n" +
                        "    \"eventTime\": \"12:00:00\",\n" +
                        "    \"location\": \"testLocation\",\n" +
                        "    \"postalCode\": \"testPostalCode\",\n" +
                        "    \"countryCode\": \"testCountry\",\n" +
                        "    \"description\": \"testDescription\",\n" +
                        "    \"category\": \"testCategory\",\n" +
                        "    \"image\": \"testImage\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON);

        Event testEvent = getEvent();
        Mockito.when(eventService.getEvent(Mockito.any())).thenReturn(testEvent);
        Mockito.when(eventService.saveEvent(Mockito.any())).thenThrow(IllegalArgumentException.class);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Exception"));
    }

    @Test
    public void testDeleteEvent() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/events/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Event testEvent = getEvent();

        Mockito.when(eventService.getEvent(1L)).thenReturn(testEvent);
        Mockito.when(eventService.deleteEvent(1L)).thenReturn(true);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus()); // Changed from HttpStatus.OK to HttpStatus.NO_CONTENT
        assertEquals("", response.getContentAsString());
    }

    @Test
    public void testDeleteEventNotFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/events/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(eventService.getEvent(1L)).thenReturn(null);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }
}
