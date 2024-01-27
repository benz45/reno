package com.reno.reno.model.order.response;

import com.reno.reno.model.product.ProductEntity;

import lombok.Data;

@Data
public class ResponseProductCount {

    Integer productCount;

    ProductEntity product;
}
