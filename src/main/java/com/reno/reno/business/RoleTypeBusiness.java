package com.reno.reno.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.role.RoleTypeEntity;
import com.reno.reno.repository.role.RoleTypeRepository;

@Component
public class RoleTypeBusiness {
    private @Autowired RoleTypeRepository roleTypeRepository;

    public RoleTypeEntity shouldGetRoleTypeByIdOrElseThrow(Integer roleTypeId) throws ApiException {
        return roleTypeRepository.findById(roleTypeId)
                .orElseThrow(() -> new ApiException("400", "Role type id not found"));
    }

}
