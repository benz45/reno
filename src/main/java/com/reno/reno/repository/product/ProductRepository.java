package com.reno.reno.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.product.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
