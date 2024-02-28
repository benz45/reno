package com.reno.reno.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.reno.reno.business.StoreCodeDiscountBusiness;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.store.StoreCodeDiscountEntity;
import com.reno.reno.model.store.request.StoreCodeDiscountRequest;

@Validated
@RestController
public class StoreCodeDiscountController {

    private @Autowired StoreCodeDiscountBusiness storeCodeDiscountBusiness;

    @PostMapping("/e-commerce-info/store-code-discount")
    public List<StoreCodeDiscountEntity> postStores(@Valid @RequestBody List<StoreCodeDiscountRequest> requests)
            throws ApiException {
        return storeCodeDiscountBusiness.createStoreCodeDiscount(requests);
    }
}
