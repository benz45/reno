package com.reno.reno.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.order.ShippingStatusTypeEntity;

public interface ShippingStatusTypeRepository extends JpaRepository<ShippingStatusTypeEntity, Integer> {

}
