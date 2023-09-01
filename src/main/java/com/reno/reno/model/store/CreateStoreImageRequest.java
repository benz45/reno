package com.reno.reno.model.store;

import lombok.Data;

@Data
public class CreateStoreImageRequest {

    private String key;

    private Integer storeImageTypeId;
}
