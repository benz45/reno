package com.reno.reno.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.reno.reno.model.base.BaseNameThNameEn;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "ecommerce_store", name = "gender")
@Data
@EqualsAndHashCode(callSuper = false)
public class GenderEntity extends BaseNameThNameEn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

}
