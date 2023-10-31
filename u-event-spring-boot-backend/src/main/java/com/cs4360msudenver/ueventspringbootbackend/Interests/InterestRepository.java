package com.cs4360msudenver.ueventspringbootbackend.Interests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends JpaRepository<Interests, Long> {
    Interests findByInterest(String interest);
}
