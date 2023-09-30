package com.reno.reno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reno.reno.model.HealthCheckEntiry;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.repository.HealthCheckRepository;

@RestController
public class HealthCheckController {

    @Autowired
    private HealthCheckRepository healthCheckRepository;

    @GetMapping
    public String getHelloWorld() throws ApiException {
        return "Hello World";
    }

    @GetMapping("/healthcheck")
    public HealthCheckEntiry getHealthCheck() throws ApiException {
        HealthCheckEntiry healthCheckEntiry = healthCheckRepository.findById(1)
                .orElseThrow(() -> new ApiException("404", "API Not working"));
        return healthCheckEntiry;
    }

}
