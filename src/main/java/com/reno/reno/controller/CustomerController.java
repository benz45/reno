package com.reno.reno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reno.reno.business.CustomerBusiness;
import com.reno.reno.model.customer.CustomerEntity;
import com.reno.reno.model.exception.ApiException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/e-commerce-info")
public class CustomerController {

    private @Autowired CustomerBusiness customerBusiness;

    @GetMapping("/customer/user/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public CustomerEntity getCustomerByUserId(@PathVariable Long id) throws ApiException {
        return customerBusiness.getCustomerByUserId(id);
    }
}
