package com.reno.reno.model.order.response;

import java.util.List;

import com.reno.reno.model.order.request.RequestProduct;

import lombok.Data;

@Data
public class ResponseOrder {

    private Long storeId;

    private Long storeCodeDiscountId;

    private List<RequestProduct> products;
}
