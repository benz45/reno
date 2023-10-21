package com.reno.reno.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.product.ProductDetailTypeEntity;
import com.reno.reno.repository.product.ProductDetailTypeRepository;
import com.reno.reno.util.Util;

@Component
public class ProductDetailTypeBusiness {
    private @Autowired ProductDetailTypeRepository productDetailTypeRepository;

    public ProductDetailTypeEntity shouldGetProductDetailTypeByIdOrElseThrow(Long productDetailTypeId)
            throws ApiException {
        if (!Util.isNotNull(productDetailTypeId)) {
            throw new ApiException("400", "Product detail type id is null.");
        }
        return productDetailTypeRepository.findById(productDetailTypeId).orElseThrow(
                () -> new ApiException("400", "Can't find product detail type id " + productDetailTypeId.toString()));
    }

    public List<ProductDetailTypeEntity> saveAllProductDetailType(List<ProductDetailTypeEntity> productDetailTypes) {
        return productDetailTypeRepository.saveAll(productDetailTypes);
    }
}
