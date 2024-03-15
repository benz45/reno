package com.reno.reno.model.store;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.reno.reno.model.CreateAddressRequest;

import lombok.Data;

@Data
public class CreateStoreRequest {

    @NotNull(message = "Store name required.")
    private String storeName;

    private String detail;

    private List<CreateStoreImageRequest> storeImages;

    private CreateAddressRequest address;
}
