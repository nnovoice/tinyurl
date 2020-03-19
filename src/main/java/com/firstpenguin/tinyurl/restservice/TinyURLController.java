package com.firstpenguin.tinyurl.restservice;

import com.firstpenguin.tinyurl.restservice.util.ShortUrlCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.codec.digest.DigestUtils;

@RestController
public class TinyURLController {
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    URLRepository urlRepository;

    @Autowired
    ShortUrlCodeGenerator<String> shortUrlCodeGenerator;

    @PostMapping(path = "/short")
    public Url shortUrl(@RequestBody Url url) {
        String sha1 = DigestUtils.sha1Hex(url.getUrl());
        System.out.println(sha1 + "length=" + sha1.length());
        url.setId(sha1);
        if (shortUrlCodeGenerator.hasNext()) {
            url.setShortUrl(shortUrlCodeGenerator.next());
        }
        return urlRepository.save(url);
    }

    @GetMapping(path="/short/{id}")
    public Optional<Url> getShortUrl(@PathVariable String id) {
        return urlRepository.findById(id);
    }
}
