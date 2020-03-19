package com.firstpenguin.tinyurl.restservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface URLRepository extends JpaRepository<Url, String> {
    Optional<Url> findById(String id);

    @Query("SELECT t FROM Url t WHERE t.shortUrl = ?1")
    Url findByShortUrl(String shortUrl);

    List<Url> findAll();
}
