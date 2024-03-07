package com.reno.reno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reno.reno.business.EmployeeBusiness;
import com.reno.reno.model.employee.EmployeeEntity;
import com.reno.reno.model.exception.ApiException;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/e-commerce-info")
public class EmployeeController {

    private @Autowired EmployeeBusiness employeeBusiness;

    @GetMapping("/employee/user/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public EmployeeEntity getEmployeeByUserId(@Valid @PathVariable Long id) throws ApiException {
        return employeeBusiness.getEmployeeByUserId(id);
    }
}
