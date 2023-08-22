package com.reno.reno.repository.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.payment.PaymentDetailEntity;

public interface PaymentDetailRepository extends JpaRepository<PaymentDetailEntity, Long> {

}
