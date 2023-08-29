package com.reno.reno.business;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.reno.reno.model.GenderEntity;
import com.reno.reno.model.customer.CustomerEntity;
import com.reno.reno.model.customer.CustomerLevalEntity;
import com.reno.reno.model.customer.CustomerRequest;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.repository.customer.CustomerRepository;

@Component
public class CustomerBusiness {

    private @Autowired CustomerRepository customerRepository;

    private @Autowired CustomerLevalBusiness customerLevalBusiness;

    @Autowired
    GenderBusiness genderBusiness;

    @Transactional(rollbackFor = Exception.class)
    public CustomerEntity createCustomer(CustomerRequest request) throws ApiException {
        checkCustomerUsernameThrowIfDuplicate(request.getUsername());
        CustomerEntity customer = convertCustomerRequestToEntity(request);
        CustomerLevalEntity customerLeval = customerLevalBusiness.shouldGetCustomerLevalByIdOrElseThrowIfNotExists(1);
        GenderEntity gender = genderBusiness.shouldGetGenderByIdOrElseThrowIfNotExists(request.getGender().getId());
        customer.setCustomerLeval(customerLeval);
        customer.setGender(gender);
        return customerRepository.save(customer);
    }

    public void checkCustomerUsernameThrowIfDuplicate(String username) throws ApiException {
        Optional<CustomerEntity> optionalCustomer = customerRepository.findByUsername(username);
        if (optionalCustomer.isPresent()) {
            throw new ApiException("400", "Username already taken.");
        }
    }

    public CustomerEntity convertCustomerRequestToEntity(CustomerRequest request) {
        CustomerEntity customer = new CustomerEntity();
        customer.setUsername(request.getUsername());
        customer.setName(request.getName());
        customer.setTal(request.getTal());
        customer.setEmail(request.getEmail());
        customer.setBirthday(request.getBirthday());
        customer.setUpdatedAt(new Date());
        customer.setCreatedAt(new Date());
        return customer;
    }
}
