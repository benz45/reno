package com.reno.reno.model.order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.reno.reno.model.base.BaseColumnCreatedIsDeleted;

@Entity
@Table(name = "shipping_status", schema = "ecommerce_store")
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
