package com.reno.reno.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.product.ProductStatusEntity;

public interface ProductStatusRepository extends JpaRepository<ProductStatusEntity, Integer> {

}
