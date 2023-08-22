package com.reno.reno.repository.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.payment.PaymentDetailTypeEntity;

public interface PaymentDetailTypeRepository extends JpaRepository<PaymentDetailTypeEntity, Integer> {

}
