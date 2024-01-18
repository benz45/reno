package com.reno.reno.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.store.CodeDiscountTypeEntity;
import com.reno.reno.repository.store.CodeDiscountTypeRepository;

@Component
public class CodeDiscountTypeBusiness {
    private @Autowired CodeDiscountTypeRepository codeDiscountTypeRepository;

    public CodeDiscountTypeEntity shouldGetCodeDiscountByIdOrElseThrows(Integer codeDiscountType)
            throws ApiException {
        return codeDiscountTypeRepository.findById(codeDiscountType)
                .orElseThrow(() -> new ApiException("400",
                        "Can't find code discount type id: " + codeDiscountType.toString()));
    }
}
