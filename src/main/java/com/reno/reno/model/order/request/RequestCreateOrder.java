package com.reno.reno.model.order.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestCreateOrder {

    @NotNull(message = "customerId required.")
    private Long customerId;

    @Valid
    @NotEmpty(message = "orders not empty.")
    @NotNull(message = "orders required.")
    private List<RequestOrder> orders;

}
