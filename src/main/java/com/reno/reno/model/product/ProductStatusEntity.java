package com.reno.reno.model.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.reno.reno.model.base.BaseNameThNameEn;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "product_status", schema = "e_commerce_info")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductStatusEntity extends BaseNameThNameEn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
