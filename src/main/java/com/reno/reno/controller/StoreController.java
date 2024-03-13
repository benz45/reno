package com.reno.reno.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reno.reno.businessflow.StoreBusinessFlow;
import com.reno.reno.model.base.PageResponse;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.store.CreateStoreRequest;
import com.reno.reno.model.store.CreateStoreResponse;
import com.reno.reno.model.store.StorePageFilter;
import com.reno.reno.model.store.StorePageResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@Validated
@RestController
@RequestMapping("/api/e-commerce-info")
public class StoreController {

    private @Autowired StoreBusinessFlow storeBusinessFlow;

    @GetMapping("/store")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public PageResponse<StorePageResponse> getStorePages(String storeName, Integer customerId, Pageable pageable)
            throws ApiException {
        StorePageFilter filter = new StorePageFilter(storeName, customerId, pageable);
        return storeBusinessFlow.getStorePages(filter);
    }

    @PostMapping("/store")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public CreateStoreResponse postStore(@Valid @RequestBody CreateStoreRequest request) throws ApiException {
        return storeBusinessFlow.createStore(request);
    }
}
