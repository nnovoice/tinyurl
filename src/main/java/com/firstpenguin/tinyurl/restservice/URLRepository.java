package com.firstpenguin.tinyurl.restservice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface URLRepository extends JpaRepository<Url, String> {
    Optional<Url> findById(String id);
    List<Url> findAll();

}
