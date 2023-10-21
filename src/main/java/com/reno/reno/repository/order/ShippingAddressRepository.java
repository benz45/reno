package com.reno.reno.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.order.ShippingAddressEntity;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddressEntity, Long> {

}
