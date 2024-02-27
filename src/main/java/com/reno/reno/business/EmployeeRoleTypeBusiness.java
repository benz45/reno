package com.reno.reno.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.employee.EmployeeRoleTypeEntity;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.repository.employee.EmployeeRoleTypeRepository;

@Component
public class EmployeeRoleTypeBusiness {
    private @Autowired EmployeeRoleTypeRepository employeeRoleTypeRepository;

    public EmployeeRoleTypeEntity shouldGetEmployeeRoleTyoeByIdOrElseThrowIfNotExists(Integer roleTypeId)
            throws ApiException {
        if (roleTypeId == null) {
            throw new ApiException("400", "Employee role type id is not null");
        }
        return employeeRoleTypeRepository.findById(roleTypeId)
                .orElseThrow(() -> new ApiException("404", "can't find employee role type id " + roleTypeId));
    }

}
