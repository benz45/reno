package com.reno.reno.businessflow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.reno.reno.business.CustomerBusiness;
import com.reno.reno.business.EmployeeBusiness;
import com.reno.reno.business.StoreBusiness;
import com.reno.reno.business.StoreImageBusiness;
import com.reno.reno.business.StoreOwnerBusiness;
import com.reno.reno.model.base.PageResponse;
import com.reno.reno.model.customer.CustomerEntity;
import com.reno.reno.model.employee.EmployeeEntity;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.store.CreateStoreRequest;
import com.reno.reno.model.store.CreateStoreResponse;
import com.reno.reno.model.store.StoreEntity;
import com.reno.reno.model.store.StoreImageEntity;
import com.reno.reno.model.store.StorePageFilter;
import com.reno.reno.model.store.StorePageResponse;

@Component
public class StoreBusinessFlow {
    private @Autowired StoreBusiness storeBusiness;
    private @Autowired StoreImageBusiness storeImageBusiness;
    private @Autowired CustomerBusiness customerBusiness;
    private @Autowired EmployeeBusiness employeeBusiness;
    private @Autowired StoreOwnerBusiness storeOwnerBusiness;

    public PageResponse<StorePageResponse> getStorePages(StorePageFilter filter) {
        String sqlQuery = storeBusiness.generateQueryString(filter);
        String sqlCount = storeBusiness.generateQueryStringCount(filter);
        List<Object[]> queryResults = storeBusiness.exceutePageableSqlString(sqlQuery);
        List<StorePageResponse> responses = storeBusiness.convertQueryToResponse(queryResults);
        Long count = storeBusiness.executeCountSqlString(sqlCount);
        return PageResponse.create(responses, count, filter.getPageable(), StorePageResponse.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public CreateStoreResponse createStoreCustomer(Long customerId, CreateStoreRequest request) throws ApiException {
        storeBusiness.checkStoreNameThrowIfDuplicate(request.getStoreName());
        storeBusiness.checkStoreImageTypeThrowIfDuplicate(request.getStoreImages());
        CustomerEntity customer = customerBusiness.getCustomerById(customerId);
        StoreEntity store = storeBusiness.shouldSaveStore(request);
        List<StoreImageEntity> storeImages = storeImageBusiness.shouldSaveStoreImage(request, store,
                customer.getId());
        storeOwnerBusiness.saveStoreOwnerWithCustomer(customer, store);
        return storeBusiness.convertToCreateStoreResponse(store, storeImages, customer.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public CreateStoreResponse createStoreEmployee(Long employeeId, CreateStoreRequest request) throws ApiException {
        storeBusiness.checkStoreNameThrowIfDuplicate(request.getStoreName());
        storeBusiness.checkStoreImageTypeThrowIfDuplicate(request.getStoreImages());
        EmployeeEntity employee = employeeBusiness.getEmployeeById(employeeId);
        StoreEntity store = storeBusiness.shouldSaveStore(request);
        List<StoreImageEntity> storeImages = storeImageBusiness.shouldSaveStoreImage(request, store,
                employee.getId());
        storeOwnerBusiness.saveStoreOwnerWithEmployee(employee, store);
        return storeBusiness.convertToCreateStoreResponse(store, storeImages, employee.getId());
    }
}
