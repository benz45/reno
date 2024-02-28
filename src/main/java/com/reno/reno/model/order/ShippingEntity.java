package com.reno.reno.model.order;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Where;

import com.reno.reno.model.base.BaseColumnCreatedIsDeleted;

@Entity
@Table(name = "shipping", schema = "e_commerce_info")
@Where(clause = "is_deleted = false")
public class ShippingEntity extends BaseColumnCreatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shipping_status_id", referencedColumnName = "id")
    private ShippingStatusEntity shippingStatus;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
    private ShippingAddressEntity shippingAddress;

}
