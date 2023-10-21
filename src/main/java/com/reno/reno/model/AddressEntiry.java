package com.reno.reno.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.reno.reno.model.base.BaseColumnCreatedUpdatedIsDeleted;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "ecommerce_store", name = "address")
@Where(clause = "is_deleted = false")
@Data
@EqualsAndHashCode(callSuper = false)
public class AddressEntiry extends BaseColumnCreatedUpdatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tal")
    private String tal;

    @Column(name = "is_active")
    private Boolean is_active;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "subdistrict")
    private String subdistrict;

    @Column(name = "district")
    private String district;

    @Column(name = "province")
    private String province;

    @Column(name = "postalcode")
    private Integer postalcode;
}
