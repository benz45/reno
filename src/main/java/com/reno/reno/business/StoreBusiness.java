package com.reno.reno.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.reno.reno.model.AddressEntiry;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.store.CreateStoreImageRequest;
import com.reno.reno.model.store.CreateStoreRequest;
import com.reno.reno.model.store.CreateStoreResponse;
import com.reno.reno.model.store.StoreEntity;
import com.reno.reno.model.store.StoreImageEntity;
import com.reno.reno.model.store.StorePageResponse;
import com.reno.reno.repository.BaseCustomRepository;
import com.reno.reno.repository.store.StoreRepository;
import com.reno.reno.util.StringUtil;
import com.reno.reno.util.Util;

import jakarta.validation.constraints.NotNull;

@Component
public class StoreBusiness extends BaseCustomRepository {

    private @Autowired PageCustomBusiness pageCustomBusiness;
    private @Autowired StoreRepository storeRepository;
    private @Autowired AddressBusiness addressBusiness;

    public List<StorePageResponse> convertQueryToResponse(List<Object[]> queryResults) {
        List<StorePageResponse> responses = new ArrayList<StorePageResponse>();
        for (Object[] queryResult : queryResults) {
            responses.add(extractStoreResponse(queryResult));
        }
        return responses;
    }

    public StoreEntity shouldGetStoreByIdOrElseThrow(@NotNull Long storeId) throws ApiException {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new ApiException("400", "Can't find store id: " + storeId));
    }

    public String generateQueryStringGetStoreCustomer(String storeName, Integer customerId, Pageable pageable) {
        StringBuilder s = new StringBuilder();
        s.append(
                "select distinct s.id, s.store_name, s.detail, i.\"key\" as store_profile_url, s.updated_at, s.created_at, count(s.id) as following_customers, so.customer_id as created_by from e_commerce_info.store s");
        s.append(generateFromQuery());
        s.append(generateFilterQueryGetStoreCustomer(storeName, customerId));
        s.append(" group by s.id, i.id, so.id order by following_customers desc\n");
        s.append(pageCustomBusiness.generatePaginationQuery(pageable));
        return s.toString();
    }

    public String generateQueryStringGetStoreEmployee(String storeName, Integer employeeId, Pageable pageable) {
        StringBuilder s = new StringBuilder();
        s.append(
                "select distinct s.id, s.store_name, s.detail, i.\"key\" as store_profile_url, s.updated_at, s.created_at, count(s.id) as following_customers, so.employee_id as created_by from e_commerce_info.store s");
        s.append(generateFromQuery());
        s.append(generateFilterQueryGetStoreEmployee(storeName, employeeId));
        s.append(" group by s.id, i.id, so.id order by following_customers desc\n");
        s.append(pageCustomBusiness.generatePaginationQuery(pageable));
        return s.toString();
    }

    public String generateQueryStringCountGetCustomer(String storeName, Integer customerId) {
        StringBuilder s = new StringBuilder();
        s.append(
                "select distinct count(s.id) from e_commerce_info.store s");
        s.append(generateFromQuery());
        s.append(generateFilterQueryGetStoreCustomer(storeName, customerId));
        return s.toString();
    }

    public String generateQueryStringCountGetEmployee(String storeName, Integer employeeId) {
        StringBuilder s = new StringBuilder();
        s.append(
                "select distinct count(s.id) from e_commerce_info.store s");
        s.append(generateFromQuery());
        s.append(generateFilterQueryGetStoreEmployee(storeName, employeeId));
        return s.toString();
    }

    public String generateFromQuery() {
        StringBuilder s = new StringBuilder();
        s.append(
                " left join e_commerce_info.store_image si on si.store_id = s.id and si.is_deleted = false");
        s.append(
                " left join e_commerce_info.store_owner so on so.store_id = s.id and so.is_deleted = false");
        s.append(
                " left join e_commerce_info.following_store fs2 on fs2.store_id = s.id and fs2.is_deleted = false and s.is_deleted = false");
        s.append(" left join e_commerce_info.image i on i.id = si.image_id and i.is_deleted = false ");
        return s.toString();
    }

    public String generateFilterQueryGetStoreCustomer(String storeName, Integer customerId) {
        StringBuilder s = new StringBuilder();
        s.append(" where true ");
        if (storeName != null) {
            s.append(" and (lower(s.store_name) like  '%"
                    + StringUtil.replaceSpecialString(storeName).toLowerCase() + "%') ");
        }
        if (customerId != null) {
            s.append(" and so.customer_id = " + customerId);
        }
        return s.toString();
    }

    public String generateFilterQueryGetStoreEmployee(String storeName, Integer employeeId) {
        StringBuilder s = new StringBuilder();
        s.append(" where true ");
        if (storeName != null) {
            s.append(" and (lower(s.store_name) like  '%"
                    + StringUtil.replaceSpecialString(storeName).toLowerCase() + "%') ");
        }
        if (employeeId != null) {
            s.append(" and so.employee_id = " + employeeId);
        }
        return s.toString();
    }

    public StorePageResponse extractStoreResponse(Object[] queryData) {
        StorePageResponse storePageResponse = new StorePageResponse();
        storePageResponse.setId(super.convertToLongFromBigInteger(queryData[0]));
        storePageResponse.setName(super.convertToString(queryData[1]));
        storePageResponse.setDetail(super.convertToString(queryData[2]));
        storePageResponse.setStoreProfileUrl(super.convertToString(queryData[3]));
        storePageResponse.setUpdatedAt(super.convertToDate(queryData[4]));
        storePageResponse.setCreatedAt(super.convertToDate(queryData[5]));
        storePageResponse.setFollowing_customers(super.convertToLongFromBigInteger(queryData[6]));
        storePageResponse.setCreatedBy(super.convertToLongFromBigInteger(queryData[7]));
        return storePageResponse;
    }

    public void checkStoreNameThrowIfDuplicate(String storeName) throws ApiException {
        Optional<StoreEntity> optionalStore = storeRepository.findByStoreName(storeName);
        if (optionalStore.isPresent()) {
            throw new ApiException("400", "Store name is already taken.");
        }
    }

    public void checkStoreImageTypeThrowIfDuplicate(List<CreateStoreImageRequest> storeImageRequests)
            throws ApiException {
        if (Util.isNotNull(storeImageRequests)) {
            HashMap<Integer, Boolean> duplicateMap = new HashMap<Integer, Boolean>();
            for (CreateStoreImageRequest storeImageRequest : storeImageRequests) {
                if (Util.isNotNull(storeImageRequest.getStoreImageTypeId())) {
                    Integer storeImageTypeId = storeImageRequest.getStoreImageTypeId();
                    if (duplicateMap.get(storeImageTypeId) != null) {
                        throw new ApiException("400", "Duplicate store image");
                    }
                    duplicateMap.put(storeImageRequest.getStoreImageTypeId(), true);
                }
            }
        }
    }

    public StoreEntity shouldSaveStore(CreateStoreRequest request) throws ApiException {
        StoreEntity store = new StoreEntity();
        if (Util.isNotNull(request.getAddress())) {
            AddressEntiry address = addressBusiness.convertRequestToAddress(request.getAddress());
            address = addressBusiness.shouldSavaAddressOrElseThrow(address);
            store.setAddress(address);
        }
        Util.setIfNotNull(request.getStoreName(), store::setStoreName);
        Util.setIfNotNull(request.getDetail(), store::setDetail);
        return storeRepository.save(store);
    }

    public CreateStoreResponse convertToCreateStoreResponse(StoreEntity store, List<StoreImageEntity> storeImages,
            Long createdById) {
        CreateStoreResponse response = new CreateStoreResponse();
        Util.setIfNotNull(store.getId(), response::setId);
        Util.setIfNotNull(store.getStoreName(), response::setStoreName);
        Util.setIfNotNull(store.getDetail(), response::setDetail);
        Util.setIfNotNull(store.getAddress(), response::setAddress);
        Util.setIfNotNull(storeImages, response::setStoreImages);
        Util.setIfNotNull(store.getCreatedAt(), response::setCreatedAt);
        Util.setIfNotNull(store.getUpdatedAt(), response::setUpdatedAt);
        Util.setIfNotNull(createdById, response::setCreatedBy);
        return response;
    }

}
