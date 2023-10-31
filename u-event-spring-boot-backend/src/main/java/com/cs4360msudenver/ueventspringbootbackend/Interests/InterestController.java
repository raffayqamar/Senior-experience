package com.cs4360msudenver.ueventspringbootbackend.Interests;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interests")
public class InterestController {

    @Autowired
    private InterestService interestService;

    @GetMapping
    public ResponseEntity<Iterable<Interests>> getInterests() {
        try {
            return new ResponseEntity<>(interestService.getInterests(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Get interest by id
    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Interests> getInterest(@PathVariable Long id) {
        try {
            Interests interest = interestService.getInterest(id);
            return new ResponseEntity<>(interest, interest == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get interest by name
    @GetMapping(path = "/{interest}")
    public ResponseEntity<Interests> getInterestByName(@PathVariable String interest) {
        try {
            Interests interests = interestService.getInterestByName(interest);
            return new ResponseEntity<>(interests, interests == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }

    // Save interest
    @PostMapping
    public ResponseEntity<Interests> saveInterest(@RequestBody Interests interest) {
        try {
            return new ResponseEntity<>(interestService.saveInterest(interest), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }

    // delete interest
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteInterest(@PathVariable Long id) {
        try {
            if(interestService.deleteInterest(id)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
