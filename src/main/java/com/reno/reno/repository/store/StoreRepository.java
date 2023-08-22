package com.reno.reno.repository.store;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.store.StoreEntity;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

}
