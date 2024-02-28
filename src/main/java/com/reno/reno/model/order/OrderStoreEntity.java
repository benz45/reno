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

import com.reno.reno.model.base.BaseColumnCreatedUpdatedIsDeleted;
import com.reno.reno.model.store.StoreCodeDiscountEntity;
import com.reno.reno.model.store.StoreEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "order_store", schema = "e_commerce_info")
@Where(clause = "is_deleted = false")
@EqualsAndHashCode(callSuper = false)
public class OrderStoreEntity extends BaseColumnCreatedUpdatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "express_company")
    private String expressCompany;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "store_code_discount_id", referencedColumnName = "id")
    private StoreCodeDiscountEntity storeCodeDiscount;

    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private StoreEntity store;

    @ManyToOne
    @JoinColumn(name = "shipping_id", referencedColumnName = "id")
    private ShippingEntity shipping;

    @ManyToOne
    @JoinColumn(name = "order_status_id", referencedColumnName = "id")
    private OrderStatusEntity orderStatus;

}
