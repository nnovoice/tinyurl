package com.firstpenguin.tinyurl.restservice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface GreetingRepository extends JpaRepository<Greeting, Long> {
    Optional<Greeting> findById(Long id);
    List<Greeting> findAll();
}
