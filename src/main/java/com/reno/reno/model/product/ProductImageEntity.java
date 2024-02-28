package com.reno.reno.model.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Where;

import com.reno.reno.model.ImageEntity;
import com.reno.reno.model.base.BaseColumnCreatedIsDeleted;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "product_image", schema = "e_commerce_info")
@Where(clause = "is_deleted = false")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductImageEntity extends BaseColumnCreatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private ImageEntity image;

}
