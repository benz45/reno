package com.reno.reno.business;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.reno.reno.constant.CustomerLevalIdConstant;
import com.reno.reno.model.GenderEntity;
import com.reno.reno.model.auth.UserEntity;
import com.reno.reno.model.customer.CustomerEntity;
import com.reno.reno.model.customer.CustomerLevalEntity;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.payload.request.SignupRequest;
import com.reno.reno.repository.customer.CustomerRepository;

import lombok.NonNull;

@Component
public class CustomerBusiness {

    private @Autowired CustomerRepository customerRepository;

    private @Autowired CustomerLevalBusiness customerLevalBusiness;

    private @Autowired GenderBusiness genderBusiness;

    @Transactional(rollbackFor = Exception.class)
    public CustomerEntity createCustomer(SignupRequest request, UserEntity user) throws ApiException {
        if (customerRepository.existsByEmail(request.getUsername())) {
            throw new ApiException("400", "Error: Email is already taken!");
        }
        CustomerEntity customer = convertCustomerRequestToEntity(request);
        CustomerLevalEntity customerLeval = customerLevalBusiness
                .shouldGetCustomerLevalByIdOrElseThrowIfNotExists(CustomerLevalIdConstant.Classic);
        GenderEntity gender = genderBusiness.shouldGetGenderByIdOrElseThrowIfNotExists(request.getGender().getId());
        customer.setCustomerLeval(customerLeval);
        customer.setGender(gender);
        customer.setAuthUserId(UUID.randomUUID().toString());
        customer.setUser(user);
        return customerRepository.save(customer);
    }

    public CustomerEntity convertCustomerRequestToEntity(SignupRequest request) {
        CustomerEntity customer = new CustomerEntity();
        customer.setName(request.getName());
        customer.setTal(request.getTal());
        customer.setEmail(request.getEmail());
        customer.setBirthday(request.getBirthday());
        customer.setUpdatedAt(new Date());
        customer.setCreatedAt(new Date());
        return customer;
    }

    public CustomerEntity getCustomerById(@NonNull Long id) throws ApiException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ApiException("400", "Can't find customer by id: " + id));
    }

    public CustomerEntity getCustomerByUserId(@NonNull Long id) throws ApiException {
        return customerRepository.findOneByUserId(id)
                .orElseThrow(() -> new ApiException("400", "Can't find customer by user id: " + id));
    }
}
