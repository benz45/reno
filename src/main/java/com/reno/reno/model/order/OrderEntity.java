package com.reno.reno.model.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.reno.reno.model.base.BaseColumnCreatedUpdatedIsDeleted;
import com.reno.reno.model.payment.PaymentDetailEntity;

@Entity
@Table(name = "order", schema = "ecommerce_store")
@Where(clause = "is_deleted = false")
public class OrderEntity extends BaseColumnCreatedUpdatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "express_company")
    private String expressCompany;

    @ManyToOne
    @JoinColumn(name = "order_product_detail_id", referencedColumnName = "id")
    private OrderProductDetailEntity orderProductDetail;

    @ManyToOne
    @JoinColumn(name = "order_status_id", referencedColumnName = "id")
    private OrderStatusEntity orderStatus;

    @ManyToOne
    @JoinColumn(name = "shipping_id", referencedColumnName = "id")
    private ShippingEntity shipping;

    @ManyToOne
    @JoinColumn(name = "payment_detail_id", referencedColumnName = "id")
    private PaymentDetailEntity paymentDetail;

}
