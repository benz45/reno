package com.reno.reno.model.product;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.Where;

import com.reno.reno.model.base.BaseColumnCreatedUpdatedIsDeleted;
import com.reno.reno.model.store.StoreEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "product", schema = "e_commerce_info")
@Where(clause = "is_deleted = false")
@Data
@EqualsAndHashCode(callSuper = false)
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    List<ProductDetailTypeEntity> productDetailType = new ArrayList<>();

    @Column(name = "is_active")
    private Boolean isActive = true;
}
