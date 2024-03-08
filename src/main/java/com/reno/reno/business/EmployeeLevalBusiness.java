package com.reno.reno.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.employee.EmployeeLevalEntity;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.repository.employee.EmployeeLevalRepository;

@Component
public class EmployeeLevalBusiness {
    private @Autowired EmployeeLevalRepository employeeLevalRepository;

    public EmployeeLevalEntity shouldGetEmployeeLevalByIdOrElseThrowIfNotExists(Integer employeeLevalId)
            throws ApiException {
        if (employeeLevalId == null) {
            throw new ApiException("400", "employee leval id is not null");
        }
        return employeeLevalRepository.findById(employeeLevalId)
                .orElseThrow(() -> new ApiException("404", "can't find employee leval id " + employeeLevalId));
    }
}
