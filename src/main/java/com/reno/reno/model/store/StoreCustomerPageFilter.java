package com.reno.reno.model.store;

import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data
public class StoreCustomerPageFilter {

    public StoreCustomerPageFilter(String storeName, Integer customerId, Pageable pageable) {
        this.storeName = storeName;
        this.customerId = customerId;
        this.pageable = pageable;
    }

    private String storeName;

    private Integer customerId;

    private Pageable pageable;
}
