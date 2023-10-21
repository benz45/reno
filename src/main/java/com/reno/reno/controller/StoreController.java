package com.reno.reno.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.reno.reno.businessflow.StoreBusinessFlow;
import com.reno.reno.model.base.PageResponse;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.store.CreateStoreRequest;
import com.reno.reno.model.store.CreateStoreResponse;
import com.reno.reno.model.store.StorePageFilter;
import com.reno.reno.model.store.StorePageResponse;

@Validated
@RestController
public class StoreController {

    private @Autowired StoreBusinessFlow storeBusinessFlow;

    @GetMapping("/store")
    public PageResponse<StorePageResponse> getStorePages(String storeName, Integer customerId, Pageable pageable)
            throws ApiException {
        StorePageFilter filter = new StorePageFilter(storeName, customerId, pageable);
        return storeBusinessFlow.getStorePages(filter);
    }

    @PostMapping("/store")
    public CreateStoreResponse postStores(@Valid @RequestBody CreateStoreRequest request) throws ApiException {
        return storeBusinessFlow.createStore(request);
    }
}
