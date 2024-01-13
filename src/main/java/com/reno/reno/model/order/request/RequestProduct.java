package com.reno.reno.model.order.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestProduct {

    @NotNull(message = "productId required.")
    private Long productId;

    @NotNull(message = "count required.")
    private Long count;

}
