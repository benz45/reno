package com.reno.reno.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.product.ProductDetailTypeEntity;

public interface ProductRepository extends JpaRepository<ProductDetailTypeEntity, Long> {

}
