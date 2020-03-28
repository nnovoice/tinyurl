package com.firstpenguin.tinyurl.restservice.controller;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstpenguin.tinyurl.restservice.entity.Url;
import com.firstpenguin.tinyurl.restservice.repository.RedisUrlRepository;
import com.firstpenguin.tinyurl.restservice.repository.URLRepository;
import com.firstpenguin.tinyurl.restservice.util.ShortUrlCodeGenerator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TinyURLController.class)
public class TinyURLControllerTest {
    private static String ID = "aaaaaaaa";
    private static String LONG_URL = "http://firstpenguin.org/somereallylongurlthat_cannot-be-easliy-shared/poda/131";
    private static String LONG_URL_HASH = "b9895441581c318beabf3768cb52458729a9e424";
    private static String EXPECTED_JSON_RESPONSE = "{\"id\":\"aaaaaaaa\"," +
            "\"url\":\"http://firstpenguin.org/somereallylongurlthat_cannot-be-easliy-shared/poda/131\"," +
            "\"longUrlHash\":\"b9895441581c318beabf3768cb52458729a9e424\"}";

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    protected MockMvc mvc;

    @MockBean
    URLRepository urlRepository;

    @MockBean
    RedisTemplate redisTemplate;

    @MockBean
    RedisUrlRepository redisUrlRepository;

    @MockBean
    ShortUrlCodeGenerator<String> shortUrlCodeGenerator;

    private static Url url;
    private static Url mockUrl;

    @BeforeAll
    static void init() {
        url = new Url("", LONG_URL, "");
        mockUrl = new Url(ID, LONG_URL, LONG_URL_HASH);
    }

    @Test
    public void testPostShortUrl_noException() throws Exception {
        when(urlRepository.findByLongUrlHash(anyString())).thenReturn(null);
        when(shortUrlCodeGenerator.hasNext()).thenReturn(true);
        when(shortUrlCodeGenerator.next()).thenReturn(ID);

        mvc.perform(post("/short")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(url)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(EXPECTED_JSON_RESPONSE));
    }

    @Test
    public void testPostShortUrl_elementExistsException() throws Exception {
        when(urlRepository.findByLongUrlHash(anyString())).thenReturn(url);

        Assertions.assertThrows(Exception.class, () ->
                mvc.perform(post("/short")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(url))));
    }

    @Test
    public void testPostShortUrl_exceptionWhenAllElementsHaveBeenGenerated() throws Exception {
        when(urlRepository.findById(anyString())).thenReturn(Optional.empty());
        when(shortUrlCodeGenerator.hasNext()).thenReturn(false);

        Assertions.assertThrows(Exception.class, () ->
                mvc.perform(post("/short")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(url))));
    }

    @Test
    public void testGetUrl() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/short/" + ID);
        when(redisUrlRepository.findById(anyString())).thenReturn(null);
        when(urlRepository.findById(anyString())).thenReturn(Optional.of(mockUrl));

        mvc.perform(requestBuilder).andExpect(content().json(EXPECTED_JSON_RESPONSE));
    }
}