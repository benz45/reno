package com.reno.reno.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.auth.UserTypeEntity;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.repository.auth.UserTypeRepository;
import com.reno.reno.util.Util;

@Component
public class UserTypeBusiness {
    private @Autowired UserTypeRepository userTypeRepository;

    public UserTypeEntity shouldGetUserTypeByIdOrElseThrow(Integer id)
            throws ApiException {
        if (!Util.isNotNull(id)) {
            throw new ApiException("400", "Product detail type id is null.");
        }
        return userTypeRepository.findById(id).orElseThrow(
                () -> new ApiException("400", "Can't find user type id " + id.toString()));
    }

}
