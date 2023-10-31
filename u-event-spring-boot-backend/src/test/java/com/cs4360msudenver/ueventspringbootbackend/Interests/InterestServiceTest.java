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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = InterestService.class)
public class InterestServiceTest {

    @MockBean
    private InterestRepository interestRepository;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private InterestService interestService;

    @BeforeEach
    public void setup() {
        interestService.entityManager = entityManager;
    }

    @Test
    public void testGetInterests() throws Exception {
        Interests testInterest = new Interests();
        testInterest.setId(1L);
        testInterest.setInterest("testInterest1");


        Interests testInterest2 = new Interests();
        testInterest2.setId(2L);
        testInterest2.setInterest("testInterest2");

        Mockito.when(interestService.getInterests()).thenReturn(Arrays.asList(testInterest, testInterest2));

        List<Interests> interests = interestService.getInterests();
        assertEquals(2L, interests.size()); //checks for two interest
        assertEquals(1L, interests.get(0).getId()); //checks whether the first interest is the right Id
        assertEquals(2L, interests.get(1).getId()); //check if second interest is the right Id


    }

    @Test
    public void testGetInterest() throws Exception {
        Interests testInterest = new Interests();
        testInterest.setId(1L);
        testInterest.setInterest("testInterest1");

        Mockito.when(interestRepository.findById(Mockito.any())).thenReturn(java.util.Optional.of(testInterest));
        Interests interest = interestService.getInterest(1L);
        assertEquals(1L, interest.getId());
        assertEquals("testInterest1", interest.getInterest());
    }

    @Test
    public void testGetInterestByName() throws Exception {
        Interests testInterest = new Interests();
        testInterest.setId(1L);
        testInterest.setInterest("testInterest1");

        Mockito.when(interestRepository.findByInterest(Mockito.any())).thenReturn(testInterest);
        Interests interest = interestService.getInterestByName("testInterest1");
        assertEquals(1L, interest.getId());
        assertEquals("testInterest1", interest.getInterest());
    }

    @Test
    public void testGetInterestByNameNotFound() throws Exception {
        Interests testInterest = new Interests();
        testInterest.setId(1L);
        testInterest.setInterest("testInterest1");

        Mockito.when(interestRepository.findByInterest(Mockito.any())).thenReturn(null);
        Interests interest = interestService.getInterestByName("testInterest1");
        assertEquals(null, interest);
    }

    @Test
    public void testSaveInterest() throws Exception {
        Interests interest = new Interests();
        interest.setInterest("testInterest1");
        interest.setId(1L);

        Mockito.when(interestRepository.saveAndFlush(Mockito.any())).thenReturn(interest);
        Mockito.when(interestRepository.save(Mockito.any())).thenReturn(interest);

        assertEquals(interest, interestService.saveInterest(interest));
    }

    @Test
    public void testSaveInterestBadRequest() throws Exception {
        Interests badInterest = new Interests();
        badInterest.setInterest("Bad Interest");

        Mockito.when(interestRepository.save(Mockito.any())).thenThrow(IllegalArgumentException.class);
        Mockito.when(interestRepository.saveAndFlush(Mockito.any())).thenThrow(IllegalArgumentException.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            interestService.saveInterest(badInterest);
        });
    }

    @Test
    public void testDeleteInterest() throws Exception {
        Interests interest = new Interests();
        interest.setInterest("testInterest1");
        interest.setId(1L);
        Mockito.when(interestRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(interest));
        Mockito.when(interestRepository.existsById(Mockito.any())).thenReturn(true);

        assertTrue(interestService.deleteInterest(interest.getId()));
    }

    @Test
    public void testDeleteInterestNotFound() throws Exception{
        Mockito.when(interestRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(interestRepository.existsById(Mockito.any())).thenReturn(false);
        Mockito.doThrow(IllegalArgumentException.class)
                .when(interestRepository)
                .deleteById(Mockito.any());

        assertFalse(interestService.deleteInterest(-1L));// Cannot have a tile with negative id
    }


}


