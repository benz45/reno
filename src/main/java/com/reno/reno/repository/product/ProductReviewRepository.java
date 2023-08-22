package com.reno.reno.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.product.ProductReviewEntity;

public interface ProductReviewRepository extends JpaRepository<ProductReviewEntity, Long> {

}
