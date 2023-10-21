package com.reno.reno.model.product;

import javax.persistence.Column;
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
@Table(name = "product_review", schema = "ecommerce_store")
@Where(clause = "is_deleted = false")
public class ProductReviewEntity extends BaseColumnCreatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity product;

    @Column(name = "product_review_detail")
    private String productReviewDetail;

    @Column(name = "satisfied_amount")
    private Integer satisfiedAmount;
}
