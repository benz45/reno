package com.reno.reno.model.order.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestCreateOrder {

    @NotNull(message = "customerId required.")
    private Long customerId;

    @NotNull(message = "paymentDetailTypeId required.")
    private Integer paymentDetailTypeId;

    @Valid
    @NotEmpty(message = "orders not empty.")
    @NotNull(message = "orders required.")
    private List<RequestOrder> orders;

}
