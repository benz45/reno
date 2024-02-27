package com.reno.reno.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.GenderEntity;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.repository.GenderRepository;

@Component
public class GenderBusiness {
    @Autowired
    private GenderRepository genderRepository;

    public GenderEntity shouldGetGenderByIdOrElseThrowIfNotExists(Integer genderId) throws ApiException {
        if (genderId == null) {
            throw new ApiException("400", "Gender id is not null");
        }
        return genderRepository.findById(genderId)
                .orElseThrow(() -> new ApiException("404", "can't find gender id " + genderId));
    }
}
