package com.reno.reno.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.product.ProductTypeEntity;

public interface ProductTypeRepository extends JpaRepository<ProductTypeEntity, Integer> {

}
