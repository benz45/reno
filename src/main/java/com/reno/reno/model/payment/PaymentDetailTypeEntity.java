package com.reno.reno.model.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.reno.reno.model.base.BaseNameThNameEn;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "payment_detail_type", schema = "e_commerce_info")
@EqualsAndHashCode(callSuper = false)
public class PaymentDetailTypeEntity extends BaseNameThNameEn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

}
