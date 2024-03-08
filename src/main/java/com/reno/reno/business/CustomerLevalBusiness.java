package com.reno.reno.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.customer.CustomerLevalEntity;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.repository.customer.CustomerLevalRepository;

@Component
public class CustomerLevalBusiness {

    private @Autowired CustomerLevalRepository customerLevalRepository;

    public CustomerLevalEntity shouldGetCustomerLevalByIdOrElseThrowIfNotExists(Integer customerLevalId)
            throws ApiException {
        return customerLevalRepository.findById(customerLevalId)
                .orElseThrow(() -> new ApiException("404", "can't find customer leval id " + customerLevalId));
    }
}
