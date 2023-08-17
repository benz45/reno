package com.reno.reno.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.HealthCheckEntiry;

public interface HealthCheckRepository extends JpaRepository<HealthCheckEntiry, Integer> {
}
