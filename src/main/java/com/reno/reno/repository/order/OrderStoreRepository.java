package com.reno.reno.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.order.OrderStoreEntity;

public interface OrderStoreRepository extends JpaRepository<OrderStoreEntity, Long> {

}
