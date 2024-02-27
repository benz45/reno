package com.reno.reno.business;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.reno.reno.constant.EmployeeRoleTypeConstant;
import com.reno.reno.model.GenderEntity;
import com.reno.reno.model.employee.EmployeeEntity;
import com.reno.reno.model.employee.EmployeeLevalEntity;
import com.reno.reno.model.employee.EmployeeRequest;
import com.reno.reno.model.employee.EmployeeRoleTypeEntity;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.service.SignUpRequest;
import com.reno.reno.model.service.signup.SignUpResponse;
import com.reno.reno.repository.employee.EmployeeRepository;
import com.reno.reno.service.supabase.SupabaseServiceImpl;

import lombok.NonNull;

@Component
public class EmployeeBusiness extends SupabaseServiceImpl {

    private @Autowired EmployeeRepository employeeRepository;
    private @Autowired EmployeeRoleTypeBusiness employeeRoleTypeBusiness;
    private @Autowired EmployeeLevalBusiness employeeLevalBusiness;
    private @Autowired GenderBusiness genderBusiness;

    @Transactional(rollbackFor = Exception.class)
    public EmployeeEntity createEmployee(EmployeeRequest request) throws Exception {
        checkEmployeeUsernameThrowIfDuplicate(request.getUsername());
        EmployeeEntity employee = convertEmployeeRequestToEntity(request);
        EmployeeLevalEntity employeeLeval = employeeLevalBusiness
                .shouldGetEmployeeLevalByIdOrElseThrowIfNotExists(1);
        GenderEntity gender = genderBusiness.shouldGetGenderByIdOrElseThrowIfNotExists(request.getGender().getId());
        EmployeeRoleTypeEntity employeeRoleType = employeeRoleTypeBusiness
                .shouldGetEmployeeRoleTyoeByIdOrElseThrowIfNotExists(EmployeeRoleTypeConstant.SE);
        employee.setEmployeeRoleType(employeeRoleType);
        employee.setEmployeeLeval(employeeLeval);
        employee.setGender(gender);
        SignUpRequest signUpEmailRequest = new SignUpRequest();
        signUpEmailRequest.setEmail(employee.getEmail());
        signUpEmailRequest.setPassword(request.getPassword());
        SignUpResponse authUser = postSignUp(signUpEmailRequest);
        employee.setAuthUserId(authUser.getUser().getId());
        return employeeRepository.save(employee);
    }

    public void checkEmployeeUsernameThrowIfDuplicate(String username) throws ApiException {
        Optional<EmployeeEntity> optionalCustomer = employeeRepository.findByUsername(username);
        if (optionalCustomer.isPresent()) {
            throw new ApiException("400", "Username already taken.");
        }
    }

    public EmployeeEntity convertEmployeeRequestToEntity(EmployeeRequest request) {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setUsername(request.getUsername());
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
}
