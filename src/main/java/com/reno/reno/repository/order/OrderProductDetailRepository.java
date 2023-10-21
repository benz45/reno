package com.reno.reno.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.order.OrderProductDetailEntity;

public interface OrderProductDetailRepository extends JpaRepository<OrderProductDetailEntity, Long> {

}
