package com.reno.reno.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.product.ProductImageEntity;

public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {

}
