package com.reno.reno.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.AddressEntiry;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.store.CreateStoreImageRequest;
import com.reno.reno.model.store.CreateStoreRequest;
import com.reno.reno.model.store.CreateStoreResponse;
import com.reno.reno.model.store.StoreEntity;
import com.reno.reno.model.store.StoreImageEntity;
import com.reno.reno.model.store.StorePageFilter;
import com.reno.reno.model.store.StorePageResponse;
import com.reno.reno.repository.BaseCustomRepository;
import com.reno.reno.repository.store.StoreRepository;
import com.reno.reno.util.StringUtil;
import com.reno.reno.util.Util;

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

    public StoreEntity shouldGetStoreByIdOrElseThrow(Long storeId) throws ApiException {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new ApiException("400", "Can't find store id: " + storeId));
    }

    public String generateQueryString(StorePageFilter filter) {
        StringBuilder s = new StringBuilder();
        s.append(
                "select distinct s.id, s.store_name, s.detail, i.\"key\" as store_profile_url, s.updated_at, s.created_at, count(s.id) as following_customers, so.customer_id as created_by from ecommerce_store.store s");
        s.append(generateFromQuery());
        s.append(generateFilterQuery(filter));
        s.append(" group by s.id, i.id, so.id order by following_customers desc\n");
        s.append(pageCustomBusiness.generatePaginationQuery(filter.getPageable()));
        return s.toString();
    }

    public String generateQueryStringCount(StorePageFilter filter) {
        StringBuilder s = new StringBuilder();
        s.append(
                "select distinct count(s.id) from ecommerce_store.store s");
        s.append(generateFromQuery());
        s.append(generateFilterQuery(filter));
        return s.toString();
    }

    public String generateFromQuery() {
        StringBuilder s = new StringBuilder();
        s.append(
                " left join ecommerce_store.store_image si on si.store_id = s.id and si.is_deleted = false");
        s.append(
                " left join ecommerce_store.store_owner so on so.store_id = s.id and so.is_deleted = false");
        s.append(
                " left join ecommerce_store.following_store fs2 on fs2.store_id = s.id and fs2.is_deleted = false and s.is_deleted = false");
        s.append(" left join ecommerce_store.image i on i.id = si.image_id and i.is_deleted = false ");
        return s.toString();
    }

    public String generateFilterQuery(StorePageFilter filter) {
        StringBuilder s = new StringBuilder();
        s.append(" where true ");
        if (filter.getStoreName() != null) {
            s.append(" and (lower(s.store_name) like  '%"
                    + StringUtil.replaceSpecialString(filter.getStoreName()).toLowerCase() + "%') ");
        }
        if (filter.getCustomerId() != null) {
            s.append(" and so.customer_id = " + filter.getCustomerId());
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

    public StoreEntity shouldSaveStore(CreateStoreRequest request) {
        StoreEntity store = new StoreEntity();
        if (Util.isNotNull(request.getAddress())) {
            AddressEntiry address = addressBusiness.saveAddress(request.getAddress());
            store.setAddress(address);
        }
        Util.setIfNotNull(request.getStoreName(), store::setStoreName);
        Util.setIfNotNull(request.getDetail(), store::setDetail);
        return storeRepository.save(store);
    }

    public CreateStoreResponse convertToCreateStoreResponse(StoreEntity store, List<StoreImageEntity> storeImages,
            Long customerId) {
        CreateStoreResponse response = new CreateStoreResponse();
        Util.setIfNotNull(store.getId(), response::setId);
        Util.setIfNotNull(store.getStoreName(), response::setStoreName);
        Util.setIfNotNull(store.getDetail(), response::setDetail);
        Util.setIfNotNull(store.getAddress(), response::setAddress);
        Util.setIfNotNull(storeImages, response::setStoreImages);
        Util.setIfNotNull(store.getCreatedAt(), response::setCreatedAt);
        Util.setIfNotNull(store.getUpdatedAt(), response::setUpdatedAt);
        Util.setIfNotNull(customerId, response::setCreatedBy);
        return response;
    }

}
