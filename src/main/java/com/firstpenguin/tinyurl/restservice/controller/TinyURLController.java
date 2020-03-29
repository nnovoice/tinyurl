package com.firstpenguin.tinyurl.restservice.controller;

import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.firstpenguin.tinyurl.restservice.entity.Url;
import com.firstpenguin.tinyurl.restservice.repository.RedisUrlRepository;
import com.firstpenguin.tinyurl.restservice.repository.URLRepository;
import com.firstpenguin.tinyurl.restservice.services.ShortURLCodeGenerationService;
import com.firstpenguin.tinyurl.restservice.util.GenerationSpaceExhaustedException;
import com.firstpenguin.tinyurl.restservice.util.ShortUrlCodeGenerator;

@RestController
public class TinyURLController {
    @Autowired
    URLRepository urlRepository;

    @Autowired
    RedisUrlRepository redisUrlRepository;

    @Autowired
    ShortURLCodeGenerationService shortUrlGenerationService;

    @PostMapping(path="/short")
    public Url shortUrl(@RequestBody Url url) throws Exception {
        String sha1 = DigestUtils.sha1Hex(url.getUrl());

        // do we have the URL already stored?
        // TODO: move this call to the Cache
        Url urlFromRepo = urlRepository.findByLongUrlHash(sha1);
        
        if (urlFromRepo != null) {
        	return urlFromRepo;
        }
        else{
        	// if the URL is not stored generate a shortURL sequence
        	try {
        		String shortURL = shortUrlGenerationService.getShortURL();
        		url.setId(shortURL);
                url.setLongUrlHash(DigestUtils.sha1Hex(url.getUrl()));

<<<<<<< .mine                // save it to the Cache
                redisUrlRepository.save(url);
=======        if (urlFromRepo != null) {
            throw new Exception("URL already exists.");
        }
>>>>>>> .theirs
<<<<<<< .mine                // save it to the DB
                return urlRepository.save(url);
        	}
        	catch(GenerationSpaceExhaustedException ex) {
        		//log the message
        	}
            
        }
=======        if (!shortUrlCodeGenerator.hasNext()) {
            throw new Exception("No more short URLs available.");
        }

        url.setId(shortUrlCodeGenerator.next());
        url.setLongUrlHash(DigestUtils.sha1Hex(url.getUrl()));

        // save it to the Cache
        redisUrlRepository.save(url);

        // save it to the DB
        urlRepository.save(url);

        return url;
>>>>>>> .theirs    }

    @GetMapping(path="/short/{id}")
    public Optional<Url> getShortUrl(@PathVariable String id) {
        Optional<Url> url = Optional.ofNullable(redisUrlRepository.findById(id));
        if (!url.isPresent()) {
            url = urlRepository.findById(id);
            redisUrlRepository.save(url.get());
        }
        return url;
    }
}
