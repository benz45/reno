package com.reno.reno.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.reno.reno.model.store.StorePageResponse;
import com.reno.reno.repository.BaseCustomRepository;
import com.reno.reno.util.StringUtil;

@Component
public class StoreBusiness extends BaseCustomRepository {
    private @Autowired PageCustomBusiness pageCustomBusiness;

    public Page<StorePageResponse> getStores(String filterStoreName, Pageable pageable) {
        String sqlString = generateQueryString(filterStoreName, pageable);
        List<Object[]> queryResults = super.exceuteSqlStringPageable(sqlString, pageable);
        List<StorePageResponse> responses = new ArrayList<StorePageResponse>();
        for (Object[] queryResult : queryResults) {
            responses.add(extractStoreResponse(queryResult));
        }
        String sqlStringCount = generateQueryStringCount(filterStoreName, pageable);
        Long count = super.executeSqlStringCount(sqlStringCount);
        return new PageImpl<>(responses, pageable, count);
    }

    private String generateQueryString(String filterStoreName, Pageable pageble) {
        StringBuilder s = new StringBuilder();
        s.append(
                "select s.id, s.store_name, s.detail, i.\"key\" as store_profile_url, s.updated_at, s.created_at, count(s.id) as following_customers from ecommerce_store.store s");
        s.append(generateFromQuery());
        s.append(generateFilterStoreNameQuery(filterStoreName));
        s.append(" group by s.id, i.id order by following_customers desc\n");
        s.append(pageCustomBusiness.generatePaginationQuery(pageble));
        return s.toString();
    }

    private String generateQueryStringCount(String filterStoreName, Pageable pageble) {
        StringBuilder s = new StringBuilder();
        s.append(
                "select count(s.id) from ecommerce_store.store s");
        s.append(generateFromQuery());
        s.append(generateFilterStoreNameQuery(filterStoreName));
        s.append(" group by s.id ");
        return s.toString();
    }

    private String generateFromQuery() {
        StringBuilder s = new StringBuilder();
        s.append(
                " left join ecommerce_store.store_image si on si.store_id = s.id and si.is_deleted = false");
        s.append(
                " left join ecommerce_store.following_store fs2 on fs2.store_id = s.id and fs2.is_deleted = false and s.is_deleted = false");
        s.append(" left join ecommerce_store.image i on i.id = si.image_id and i.is_deleted = false ");
        return s.toString();
    }

    private String generateFilterStoreNameQuery(String filterStoreName) {
        StringBuilder s = new StringBuilder();
        if (filterStoreName != null) {
            s.append(" where (lower(s.store_name) like  '%"
                    + StringUtil.replaceSpecialString(filterStoreName).toLowerCase() + "%') ");
        }
        return s.toString();
    }

    private StorePageResponse extractStoreResponse(Object[] queryData) {
        StorePageResponse storePageResponse = new StorePageResponse();
        storePageResponse.setId(super.extractLongDataFromBigIntegerObject(queryData[0]));
        storePageResponse.setName(super.extractStringSafty(queryData[1]));
        storePageResponse.setDetail(super.extractStringSafty(queryData[2]));
        storePageResponse.setStoreProfileUrl(super.extractStringSafty(queryData[3]));
        storePageResponse.setUpdatedAt(super.extractDateSafty(queryData[4]));
        storePageResponse.setCreatedAt(super.extractDateSafty(queryData[5]));
        storePageResponse.setFollowing_customers(super.extractLongDataFromBigIntegerObject(queryData[6]));
        return storePageResponse;

    }
}
