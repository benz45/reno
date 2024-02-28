package com.reno.reno.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.reno.reno.business.CustomerBusiness;
import com.reno.reno.model.customer.CustomerEntity;
import com.reno.reno.model.customer.CustomerRequest;

@Validated
@RestController
public class CustomerController {
    @Autowired
    CustomerBusiness customerBusiness;

    @PostMapping("/e-commerce-info/customer")
    public CustomerEntity postCustomeCustomer(@Valid @RequestBody CustomerRequest request) throws Exception {
        return customerBusiness.createCustomer(request);
    }
}
