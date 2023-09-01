package com.reno.reno.businessflow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.reno.reno.business.CustomerBusiness;
import com.reno.reno.business.StoreBusiness;
import com.reno.reno.business.StoreImageBusiness;
import com.reno.reno.business.StoreOwnerBusiness;
import com.reno.reno.model.customer.CustomerEntity;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.store.CreateStoreRequest;
import com.reno.reno.model.store.CreateStoreResponse;
import com.reno.reno.model.store.StoreEntity;
import com.reno.reno.model.store.StoreImageEntity;
import com.reno.reno.model.store.StorePageResponse;

@Component
public class StoreBusinessFlow {
    private @Autowired StoreBusiness storeBusiness;
    private @Autowired StoreImageBusiness storeImageBusiness;
    private @Autowired CustomerBusiness customerBusiness;
    private @Autowired StoreOwnerBusiness storeOwnerBusiness;

    public Page<StorePageResponse> getStorePages(String filterStoreName, Pageable pageable) {
        String sqlString = storeBusiness.generateQueryString(filterStoreName, pageable);
        List<Object[]> queryResults = storeBusiness.exceutePageableSqlString(sqlString, pageable);
        List<StorePageResponse> responses = storeBusiness.convertQueryToResponse(queryResults);
        String sqlStringCount = storeBusiness.generateQueryStringCount(filterStoreName, pageable);
        Long count = storeBusiness.executeCountSqlString(sqlStringCount);
        return new PageImpl<>(responses, pageable, count);
    }

    @Transactional(rollbackFor = Exception.class)
    public CreateStoreResponse createStore(CreateStoreRequest request) throws ApiException {
        storeBusiness.checkStoreNameThrowIfDuplicate(request.getStoreName());
        storeBusiness.checkStoreImageTypeThrowIfDuplicate(request.getStoreImages());
        CustomerEntity customer = customerBusiness.getCustomerById(request.getCustomerId());
        StoreEntity store = storeBusiness.shouldSaveStore(request);
        List<StoreImageEntity> storeImages = storeImageBusiness.shouldSaveStoreImage(request.getStoreImages(), store,
                customer);
        storeOwnerBusiness.saveStoreOwner(customer, store);
        return storeBusiness.convertToCreateStoreResponse(store, storeImages, customer.getId());
    }
}
