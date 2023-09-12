package com.reno.reno.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.product.ProductStatusEntity;
import com.reno.reno.repository.product.ProductStatusRepository;

@Component
public class ProductStatusBusiness {
    private @Autowired ProductStatusRepository productStatusRepository;

    public ProductStatusEntity shouldGetProductStatusByIdOrElseThrow(Integer productStatusId)
            throws ApiException {
        return productStatusRepository.findById(productStatusId).orElseThrow(
                () -> new ApiException("400", "Can't find product status id " + productStatusId.toString()));
    }
}
