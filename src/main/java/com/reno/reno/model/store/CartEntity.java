package com.reno.reno.model.store;

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
import com.reno.reno.model.customer.CustomerEntity;
import com.reno.reno.model.product.ProductEntity;

@Entity
@Table(name = "cart", schema = "ecommerce_store")
@Where(clause = "is_deleted = false")
public class CartEntity extends BaseColumnCreatedUpdatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity product;

    @Column(name = "is_check_out")
    private Boolean isCheckOut = false;

}
