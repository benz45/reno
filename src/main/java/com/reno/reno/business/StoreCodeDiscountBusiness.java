package com.reno.reno.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.store.StoreCodeDiscountEntity;
import com.reno.reno.repository.store.StoreCodeDiscountRepository;

@Component
public class StoreCodeDiscountBusiness {
    private @Autowired StoreCodeDiscountRepository storeCodeDiscountRepository;

    public StoreCodeDiscountEntity shouldGetStoreCodeDiscountByIdOrElseThrows(Long storeCodeDiscountId)
            throws ApiException {
        return storeCodeDiscountRepository.findById(storeCodeDiscountId)
                .orElseThrow(() -> new ApiException("400",
                        "Can't find store code discount id: " + storeCodeDiscountId.toString()));
    }
}
