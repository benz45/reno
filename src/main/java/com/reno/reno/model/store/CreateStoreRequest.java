package com.reno.reno.model.store;

import java.util.List;

import com.reno.reno.model.CreateAddressRequest;

import lombok.Data;

@Data
public class CreateStoreRequest {

    private String storeName;

    private String detail;

    private List<CreateStoreImageRequest> storeImages;

    private CreateAddressRequest address;
}
