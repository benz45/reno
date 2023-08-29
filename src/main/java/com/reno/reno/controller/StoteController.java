package com.reno.reno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reno.reno.business.StoreBusiness;
import com.reno.reno.model.store.StorePageResponse;

@RestController
public class StoteController {

    private @Autowired StoreBusiness storeBusiness;

    @GetMapping("/store")
    public Page<StorePageResponse> getStores(String storeName, Pageable pageable) {
        return storeBusiness.getStores(storeName, pageable);
    }
}
