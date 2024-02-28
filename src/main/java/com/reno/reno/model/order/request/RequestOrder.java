package com.reno.reno.model.order.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestOrder {

    @NotNull(message = "storeId required.")
    private Long storeId;

    private Long storeCodeDiscountId;

    @Valid
    @NotEmpty(message = "products not empty.")
    @NotNull(message = "products required.")
    private List<RequestProduct> products;
}
