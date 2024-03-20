package com.reno.reno.model.store;

import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data
public class StoreEmployeePageFilter {

    public StoreEmployeePageFilter(String storeName, Integer employeeId, Pageable pageable) {
        this.storeName = storeName;
        this.employeeId = employeeId;
        this.pageable = pageable;
    }

    private String storeName;

    private Integer employeeId;

    private Pageable pageable;
}
