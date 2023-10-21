package com.reno.reno.repository.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.product.ProductImageEntity;

public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {
    List<ProductImageEntity> findAllByProductId(Long productId);
}
