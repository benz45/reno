package com.reno.reno.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.order.OrderStatusEntity;

public interface OrderStatusRepository extends JpaRepository<OrderStatusEntity, Integer> {

}
