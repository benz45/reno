package com.reno.reno.model.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Where;

import com.reno.reno.model.AddressEntiry;
import com.reno.reno.model.base.BaseColumnCreatedIsDeleted;

@Entity
@Table(name = "shipping_address", schema = "e_commerce_info")
@Where(clause = "is_deleted = false")
public class ShippingAddressEntity extends BaseColumnCreatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntiry address;

}
