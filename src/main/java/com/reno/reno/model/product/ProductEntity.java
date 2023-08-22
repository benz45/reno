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

import com.reno.reno.model.base.BaseColumnCreatedUpdatedIsDeleted;
import com.reno.reno.model.store.StoreEntity;

@Entity
@Table(name = "product", schema = "ecommerce_store")
@Where(clause = "is_deleted = false")
public class ProductEntity extends BaseColumnCreatedUpdatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "detail")
    private String detail;

    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private StoreEntity store;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private ProductStatusEntity productStatus;

    @ManyToOne
    @JoinColumn(name = "product_detail_type_id", referencedColumnName = "id")
    private ProductDetailTypeEntity productDetailType;

    @Column(name = "is_active")
    private Boolean isActive;
}
