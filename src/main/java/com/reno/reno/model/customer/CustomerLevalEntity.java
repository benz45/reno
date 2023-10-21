package com.reno.reno.model.customer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.reno.reno.model.base.BaseNameThNameEn;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "customer_leval", schema = "ecommerce_store")
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerLevalEntity extends BaseNameThNameEn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

}
