package com.firstpenguin.tinyurl.restservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class GreetingController {
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    GreetingRepository greetingRepository;

    private final String template = "Hello %s";

    @PostMapping(path="/greeting", consumes="application/json", produces = "application/json")
    public void greetingPost(@RequestBody Greeting greeting) {
        System.out.println(String.format("request body id=%d,content=%s", greeting.getId(), greeting.getContent()));
        greetingRepository.save(greeting);
    }

    @GetMapping("/greeting/{id}")
    public Greeting greetingGet(@PathVariable Long id) {
        System.out.println(String.format("Given id=%d", id));
        Greeting aGreeting = greetingRepository.findById(id).get();
        return aGreeting;
    }



}
