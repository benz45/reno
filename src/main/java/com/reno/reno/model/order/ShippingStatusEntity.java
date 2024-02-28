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
@Table(name = "shipping_status", schema = "e_commerce_info")
@Where(clause = "is_deleted = false")
public class ShippingStatusEntity extends BaseColumnCreatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shipping_id", referencedColumnName = "id")
    private ShippingEntity shipping;

    @ManyToOne
    @JoinColumn(name = "shipping_status_type_id", referencedColumnName = "id")
    private ShippingStatusTypeEntity shippingStatusType;

}
