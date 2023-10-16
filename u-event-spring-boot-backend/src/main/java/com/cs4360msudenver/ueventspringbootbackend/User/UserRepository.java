package com.cs4360msudenver.ueventspringbootbackend.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
