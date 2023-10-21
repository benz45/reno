package com.reno.reno.repository.store;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.store.StoreOwnerEntity;

public interface StoreOwnerRepository extends JpaRepository<StoreOwnerEntity, Long> {
    Optional<StoreOwnerEntity> findByStoreId(Long storeId);
}
