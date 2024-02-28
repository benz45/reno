package com.reno.reno.model.product;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateProductImageRequest {

    @NotNull(message = "Product image key required.")
    private String key;

}
