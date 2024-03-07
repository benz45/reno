package com.reno.reno.repository.customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.customer.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findOneByUserId(Long userId);

    Boolean existsByEmail(String email);
}
