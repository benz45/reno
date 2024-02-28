package com.reno.reno.model.store;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateStoreImageRequest {

    @NotNull(message = "Store image key required.")
    private String key;

    @NotNull(message = "Store image type id required.")
    private Integer storeImageTypeId;
}
