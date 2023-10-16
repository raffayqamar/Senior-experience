package com.cs4360msudenver.ueventspringbootbackend.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    protected EntityManager entityManager;


    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(String email) {
        try {
            return userRepository.findById(email).get();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public User saveUser(User user) {
        user = userRepository.saveAndFlush(user);
        entityManager.refresh(user);
        return user;
    }

    public boolean deleteUser(String email) {
        try {
            if(userRepository.existsById((email))){
                userRepository.deleteById(email);
                return true;
            }
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }

        return false;
    }

    public User register(RegistrationDto registrationDto) {
        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        return userRepository.save(user);
    }
}
