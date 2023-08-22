package com.reno.reno.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.order.ShippingStatusEntity;

public interface ShippingStatusRepository extends JpaRepository<ShippingStatusEntity, Long> {

}
