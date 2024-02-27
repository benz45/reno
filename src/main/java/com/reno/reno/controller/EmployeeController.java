package com.reno.reno.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.reno.reno.business.EmployeeBusiness;
import com.reno.reno.model.employee.EmployeeEntity;
import com.reno.reno.model.employee.EmployeeRequest;

@Validated
@RestController
public class EmployeeController {
    private @Autowired EmployeeBusiness employeeBusiness;

    @PostMapping("/employee")
    public EmployeeEntity postEmployee(@Valid @RequestBody EmployeeRequest request) throws Exception {
        return employeeBusiness.createEmployee(request);
    }
}
