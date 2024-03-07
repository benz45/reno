package com.reno.reno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reno.reno.model.HealthCheckEntiry;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.repository.HealthCheckRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/e-commerce-info")
public class HealthCheckController {

    @Autowired
    private HealthCheckRepository healthCheckRepository;

    @GetMapping("/healthcheck")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public HealthCheckEntiry getHealthCheck() throws ApiException {
        HealthCheckEntiry healthCheckEntiry = healthCheckRepository.findById(1)
                .orElseThrow(() -> new ApiException("404", "API Not working"));
        return healthCheckEntiry;
    }

}
