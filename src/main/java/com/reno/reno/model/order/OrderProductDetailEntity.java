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

import com.reno.reno.model.base.BaseColumnCreatedIsDeleted;
import com.reno.reno.model.product.ProductEntity;

@Entity
@Table(name = "order_product_detail", schema = "ecommerce_store")
@Where(clause = "is_deleted = false")
public class OrderProductDetailEntity extends BaseColumnCreatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity product;

    @Column(name = "price")
    private Integer price;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "amount")
    private Integer amount;

}
