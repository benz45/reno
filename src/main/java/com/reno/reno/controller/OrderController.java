package com.reno.reno.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.reno.reno.business.OrderBusiness;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.order.OrderEntity;
import com.reno.reno.model.order.request.RequestCreateOrder;

@Validated
@RestController
public class OrderController {

    private @Autowired OrderBusiness orderBusiness;

    @PostMapping("/order")
    @ResponseBody
    public OrderEntity createOrder(@Valid @RequestBody RequestCreateOrder requestCreateOrder) throws ApiException {
        return orderBusiness.createOrder(requestCreateOrder);
    }

}