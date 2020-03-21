package com.firstpenguin.tinyurl.restservice.repository;

import com.firstpenguin.tinyurl.restservice.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface URLRepository extends JpaRepository<Url, String> {
    Optional<Url> findById(String id);

    @Query("SELECT t FROM Url t WHERE t.longUrlHash = ?1")
    Url findByLongUrlHash(String longUrlHash);

    List<Url> findAll();
}
