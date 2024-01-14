package com.reno.reno.model.order;

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

    @ManyToOne
    @JoinColumn(name = "order_status_id", referencedColumnName = "id")
    private OrderStatusEntity orderStatus;

    @ManyToOne
    @JoinColumn(name = "payment_detail_id", referencedColumnName = "id")
    private PaymentDetailEntity paymentDetail;

}
