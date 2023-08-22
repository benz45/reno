package com.reno.reno.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.order.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
