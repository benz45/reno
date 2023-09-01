package com.reno.reno.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.customer.CustomerEntity;
import com.reno.reno.model.store.StoreEntity;
import com.reno.reno.model.store.StoreOwnerEntity;
import com.reno.reno.repository.store.StoreOwnerRepository;

@Component
public class StoreOwnerBusiness {

    private @Autowired StoreOwnerRepository storeOwnerRepository;

    public StoreOwnerEntity saveStoreOwner(CustomerEntity customer, StoreEntity store) {
        StoreOwnerEntity storeOwner = new StoreOwnerEntity();
        storeOwner.setCustomer(customer);
        storeOwner.setStore(store);
        storeOwner.setCreatedAt(new Date());
        return storeOwnerRepository.save(storeOwner);
    }
}
