package com.reno.reno.model.payment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.reno.reno.model.base.BaseNameThNameEn;

@Entity
@Table(name = "payment_detail_type", schema = "ecommerce_store")
public class PaymentDetailTypeEntity extends BaseNameThNameEn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

}
