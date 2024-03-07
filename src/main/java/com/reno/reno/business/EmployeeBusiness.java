package com.reno.reno.business;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.reno.reno.constant.EmployeeLevalIdConstant;
import com.reno.reno.constant.EmployeeRoleTypeConstant;
import com.reno.reno.model.GenderEntity;
import com.reno.reno.model.auth.UserEntity;
import com.reno.reno.model.employee.EmployeeEntity;
import com.reno.reno.model.employee.EmployeeLevalEntity;
import com.reno.reno.model.employee.EmployeeRoleTypeEntity;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.payload.request.SignupRequest;
import com.reno.reno.repository.employee.EmployeeRepository;

import lombok.NonNull;

@Component
public class EmployeeBusiness {

    private @Autowired EmployeeRepository employeeRepository;
    private @Autowired EmployeeRoleTypeBusiness employeeRoleTypeBusiness;
    private @Autowired EmployeeLevalBusiness employeeLevalBusiness;
    private @Autowired GenderBusiness genderBusiness;

    @Transactional(rollbackFor = Exception.class)
    public EmployeeEntity createEmployee(SignupRequest request, UserEntity user) throws Exception {
        checkEmployeeEmailThrowIfDuplicate(request.getEmail());
        EmployeeEntity employee = convertEmployeeRequestToEntity(request);
        EmployeeLevalEntity employeeLeval = employeeLevalBusiness
                .shouldGetEmployeeLevalByIdOrElseThrowIfNotExists(EmployeeLevalIdConstant.Classic);
        GenderEntity gender = genderBusiness.shouldGetGenderByIdOrElseThrowIfNotExists(request.getGender().getId());
        EmployeeRoleTypeEntity employeeRoleType = employeeRoleTypeBusiness
                .shouldGetEmployeeRoleTyoeByIdOrElseThrowIfNotExists(EmployeeRoleTypeConstant.SE);
        employee.setEmployeeRoleType(employeeRoleType);
        employee.setEmployeeLeval(employeeLeval);
        employee.setGender(gender);
        employee.setAuthUserId(UUID.randomUUID().toString());
        employee.setUser(user);
        return employeeRepository.save(employee);
    }

    public void checkEmployeeEmailThrowIfDuplicate(String email) throws ApiException {
        if (employeeRepository.existsByEmail(email)) {
            throw new ApiException("400", "Email already taken.");
        }
    }

    public EmployeeEntity convertEmployeeRequestToEntity(SignupRequest request) {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setName(request.getName());
        employee.setTal(request.getTal());
        employee.setEmail(request.getEmail());
        employee.setBirthday(request.getBirthday());
        employee.setUpdatedAt(new Date());
        employee.setCreatedAt(new Date());
        return employee;
    }

    public EmployeeEntity getEmployeeById(@NonNull Long id) throws ApiException {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ApiException("400", "Can't find employee by id: " + id));
    }

    public EmployeeEntity getEmployeeByUserId(@NonNull Long id) throws ApiException {
        return employeeRepository.findOneByUserId(id)
                .orElseThrow(() -> new ApiException("400", "Can't find employee by user id: " + id));
    }
}
