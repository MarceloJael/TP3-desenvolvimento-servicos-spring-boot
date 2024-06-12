package com.tp3.tp3.service;

import com.tp3.tp3.model.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CursoCacheService {

    private static final String CURSO_CACHE_KEY = "CURSO_CACHE";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void cacheCurso(Curso curso) {
        redisTemplate.opsForHash().put(CURSO_CACHE_KEY, curso.getId().toString(), curso);
        redisTemplate.expire(CURSO_CACHE_KEY, 1, TimeUnit.HOURS);
    }

    public Curso getCursoFromCache(Long id) {
        return (Curso) redisTemplate.opsForHash().get(CURSO_CACHE_KEY, id.toString());
    }

    public void evictCursoFromCache(Long id) {
        redisTemplate.opsForHash().delete(CURSO_CACHE_KEY, id.toString());
    }
}
