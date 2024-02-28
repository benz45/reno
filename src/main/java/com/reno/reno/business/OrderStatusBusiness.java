package com.reno.reno.business;

import jakarta.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.order.OrderStatusEntity;
import com.reno.reno.repository.order.OrderStatusRepository;

@Component
public class OrderStatusBusiness {

    private @Autowired OrderStatusRepository orderStatusRepository;

    public OrderStatusEntity shouldGetOrderStatusById(@NotNull Integer orderStatusId) throws ApiException {
        return orderStatusRepository.findById(orderStatusId)
                .orElseThrow(() -> new ApiException("400", "Can't find order status id " + orderStatusId.toString()));
    }
}
