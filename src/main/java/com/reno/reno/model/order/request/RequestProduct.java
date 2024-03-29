package com.reno.reno.model.order.request;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestProduct {

    @NotNull(message = "productId required.")
    private Long productId;

    @NotNull(message = "amount required.")
    private Integer amount;

}
