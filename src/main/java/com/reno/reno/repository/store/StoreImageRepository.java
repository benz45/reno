package com.reno.reno.repository.store;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.store.StoreEntity;

public interface StoreImageRepository extends JpaRepository<StoreEntity, Long> {

}
