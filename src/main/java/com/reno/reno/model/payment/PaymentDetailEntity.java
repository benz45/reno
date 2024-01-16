package com.reno.reno.model.payment;

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

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "payment_detail", schema = "ecommerce_store")
@Where(clause = "is_deleted = false")
@EqualsAndHashCode(callSuper = false)
public class PaymentDetailEntity extends BaseColumnCreatedUpdatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_paid_success")
    private Boolean isPaidSuccess = false;

    @ManyToOne
    @JoinColumn(name = "payment_detail_type_id", referencedColumnName = "id")
    private PaymentDetailTypeEntity paymentDetailType;

}
