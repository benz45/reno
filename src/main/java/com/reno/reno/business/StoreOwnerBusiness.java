package com.reno.reno.business;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.customer.CustomerEntity;
import com.reno.reno.model.employee.EmployeeEntity;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.store.StoreEntity;
import com.reno.reno.model.store.StoreOwnerEntity;
import com.reno.reno.repository.store.StoreOwnerRepository;

@Component
public class StoreOwnerBusiness {

  private @Autowired StoreOwnerRepository storeOwnerRepository;

  public StoreOwnerEntity getStoreOwnerByStoreIdOrElseThrow(Long storeId) throws ApiException {
    Optional<StoreOwnerEntity> storeOwener = storeOwnerRepository.findByStoreId(storeId);
    if (storeOwener.isEmpty()) {
      throw new ApiException("400", "Can't find store owener by store id store id " + storeId.toString());
    }
    return storeOwener.get();
  }

  public StoreOwnerEntity saveStoreOwnerWithCustomer(CustomerEntity customer, StoreEntity store) {
    StoreOwnerEntity storeOwner = new StoreOwnerEntity();
    storeOwner.setCustomer(customer);
    storeOwner.setStore(store);
    storeOwner.setCreatedAt(new Date());
    return storeOwnerRepository.save(storeOwner);
  }

  public StoreOwnerEntity saveStoreOwnerWithEmployee(EmployeeEntity employee, StoreEntity store) {
    StoreOwnerEntity storeOwner = new StoreOwnerEntity();
    storeOwner.setEmployee(employee);
    storeOwner.setStore(store);
    storeOwner.setCreatedAt(new Date());
    return storeOwnerRepository.save(storeOwner);
  }
}
