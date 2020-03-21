package com.firstpenguin.tinyurl.restservice.repository;

import com.firstpenguin.tinyurl.restservice.entity.Url;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisUrlRepository {
    private HashOperations hashOperations;

    private RedisTemplate redisTemplate;

    public RedisUrlRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    public void save(Url url) {
        hashOperations.put("url", url.getId(), url);
    }

    public Url findById(String id) {
        return (Url) hashOperations.get("url", id);
    }
}
