package com.reno.reno.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.order.ShippingEntity;

public interface ShippingRepository extends JpaRepository<ShippingEntity, Long> {

}
