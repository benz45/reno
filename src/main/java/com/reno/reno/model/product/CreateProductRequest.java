package com.reno.reno.model.product;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateProductRequest {
    @NotNull(message = "Name required.")
    private String name;

    @NotNull(message = "Price required.")
    private Integer price;

    @NotNull(message = "Amount required.")
    private Integer amount;

    private String detail;

    @NotNull(message = "Store id required.")
    private Long storeId;

    private ProductStatusEntity productStatus;

    @NotNull(message = "Product types required.")
    private List<ProductTypeEntity> productTypes;

    @NotNull(message = "Product images required.")
    private List<CreateProductImageRequest> productImages;

    private Boolean isActive;
}
