package com.reno.reno.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reno.reno.businessflow.StoreBusinessFlow;
import com.reno.reno.model.base.PageResponse;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.store.CreateStoreRequest;
import com.reno.reno.model.store.CreateStoreResponse;
import com.reno.reno.model.store.StoreCustomerPageFilter;
import com.reno.reno.model.store.StoreEmployeePageFilter;
import com.reno.reno.model.store.StorePageResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@Validated
@RestController
@RequestMapping("/api/e-commerce-info")
public class StoreController {

    private @Autowired StoreBusinessFlow storeBusinessFlow;

    @GetMapping("/store/customer")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public PageResponse<StorePageResponse> getStoreCustomerPages(String storeName, Integer customerId,
            Pageable pageable)
            throws ApiException {
        StoreCustomerPageFilter filter = new StoreCustomerPageFilter(storeName, customerId, pageable);
        return storeBusinessFlow.getStoreCustomerPages(filter);
    }

    @GetMapping("/store/employee")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public PageResponse<StorePageResponse> getStoreEmployeePages(String storeName, Integer employeeId,
            Pageable pageable)
            throws ApiException {
        StoreEmployeePageFilter filter = new StoreEmployeePageFilter(storeName, employeeId, pageable);
        return storeBusinessFlow.getStoreEmployeePages(filter);
    }

    @PostMapping("/store/customer/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public CreateStoreResponse postStoreCustomer(@PathVariable Long id, @Valid @RequestBody CreateStoreRequest request)
            throws ApiException {
        return storeBusinessFlow.createStoreCustomer(id, request);
    }

    @PostMapping("/store/employee/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public CreateStoreResponse postStoreEmployee(@PathVariable Long id, @Valid @RequestBody CreateStoreRequest request)
            throws ApiException {
        return storeBusinessFlow.createStoreEmployee(id, request);
    }
}
