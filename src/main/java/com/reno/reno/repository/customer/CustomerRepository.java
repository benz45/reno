package com.reno.reno.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.customer.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Boolean existsByEmail(String email);
}
