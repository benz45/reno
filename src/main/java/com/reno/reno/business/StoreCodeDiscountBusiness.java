package com.reno.reno.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.store.CodeDiscountTypeEntity;
import com.reno.reno.model.store.StoreCodeDiscountEntity;
import com.reno.reno.model.store.StoreEntity;
import com.reno.reno.model.store.request.StoreCodeDiscountRequest;
import com.reno.reno.repository.store.StoreCodeDiscountRepository;
import com.reno.reno.util.Util;

@Component
public class StoreCodeDiscountBusiness {
    private @Autowired StoreBusiness storeBusiness;
    private @Autowired StoreCodeDiscountRepository storeCodeDiscountRepository;
    private @Autowired CodeDiscountTypeBusiness codeDiscountTypeBusiness;

    public StoreCodeDiscountEntity shouldGetStoreCodeDiscountByIdOrElseThrows(Long storeCodeDiscountId)
            throws ApiException {
        return storeCodeDiscountRepository.findById(storeCodeDiscountId)
                .orElseThrow(() -> new ApiException("400",
                        "Can't find store code discount id: " + storeCodeDiscountId.toString()));
    }

    @Transactional(rollbackFor = Exception.class)
    public List<StoreCodeDiscountEntity> createStoreCodeDiscount(List<StoreCodeDiscountRequest> requests)
            throws ApiException {
        List<StoreCodeDiscountEntity> storeCodeDiscounts = new ArrayList<StoreCodeDiscountEntity>();
        for (StoreCodeDiscountRequest request : requests) {
            StoreCodeDiscountEntity storeCodeDiscount = new StoreCodeDiscountEntity();
            StoreEntity store = storeBusiness.shouldGetStoreByIdOrElseThrow(request.getStoreId());
            CodeDiscountTypeEntity codeDiscountType = codeDiscountTypeBusiness
                    .shouldGetCodeDiscountByIdOrElseThrows(request.getCodeDiscountTypeId());
            storeCodeDiscount.setStore(store);
            storeCodeDiscount.setCodeText(request.getCodeText());
            storeCodeDiscount.setCodeAmount(request.getCodeAmount());
            storeCodeDiscount.setDiscountAmount(request.getDiscountAmount());
            storeCodeDiscount.setCodeDiscountType(codeDiscountType);
            if (Util.isNotNull(request.getMinPurchase()) && request.getMinPurchase() > 0) {
                storeCodeDiscount.setMinAmount(request.getMinPurchase());
            }
            if (Util.isNotNull(request.getMaxDiscount()) && request.getMaxDiscount() > 0) {
                storeCodeDiscount.setMaxDiscount(request.getMaxDiscount());
            }
            if (Util.isNotNull(request.getExpridDate()) && request.getExpridDate().compareTo(new Date()) > 0) {
                storeCodeDiscount.setExpridDate(request.getExpridDate());
            }
            storeCodeDiscount.setIsActive(request.getIsActive());
            storeCodeDiscount.setCreatedAt(new Date());
            storeCodeDiscount.setUpdatedAt(new Date());
            storeCodeDiscounts.add(storeCodeDiscount);
        }
        return storeCodeDiscountRepository.saveAll(storeCodeDiscounts);
    }
}
