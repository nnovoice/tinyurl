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
    public Url shortUrl(@RequestBody Url url) throws Exception {
        String sha1 = DigestUtils.sha1Hex(url.getUrl());

        // do we have the URL already stored?
        Url urlFromRepo = urlRepository.findByLongUrlHash(sha1);

        // if the URL is not stored and there is a shortURL code
        if (urlFromRepo == null && shortUrlCodeGenerator.hasNext()) {
            url.setId(shortUrlCodeGenerator.next());
            url.setLongUrlHash(DigestUtils.sha1Hex(url.getUrl()));
            return urlRepository.save(url);
        } else {
            throw new Exception("URL already exists.");
        }
    }

    @GetMapping(path="/short/{id}")
    public Optional<Url> getShortUrl(@PathVariable String id) {
        return urlRepository.findById(id);
    }
}
