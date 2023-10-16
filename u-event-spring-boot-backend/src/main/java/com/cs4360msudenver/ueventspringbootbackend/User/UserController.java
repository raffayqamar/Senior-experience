package com.cs4360msudenver.ueventspringbootbackend.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Iterable<User>> getUsers() {
        try {
            return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{email}", produces = "application/json")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        try {
            User user = userService.getUser(email);
            return new ResponseEntity<>(user, user == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/{email}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updateUser(@PathVariable("email") String email, @RequestBody User updatedUser) {
        User retrievedUser = userService.getUser(email);
        try {
            if (retrievedUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            retrievedUser.setFirstName(updatedUser.getFirstName());
            retrievedUser.setLastName(updatedUser.getLastName());
            retrievedUser.setPassword(updatedUser.getPassword());

            return ResponseEntity.ok(userService.saveUser(retrievedUser));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/{email}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("email") String email) {
        try {
            userService.deleteUser(email);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/register")
    public ResponseEntity<User> register(@RequestBody RegistrationDto registrationDto) {
        User registeredUser = userService.register(registrationDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}
