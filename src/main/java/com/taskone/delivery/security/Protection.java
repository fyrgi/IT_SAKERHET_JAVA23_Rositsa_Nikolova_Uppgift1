package com.taskone.delivery.security;

import io.github.bucket4j.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Duration;

@Configuration
public class Protection {
    @Bean
    public Bucket bucket(){

        return Bucket4j.builder().addLimit(Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1)))).build();
    }

}