package com.reno.reno.repository.store;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.store.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

}
