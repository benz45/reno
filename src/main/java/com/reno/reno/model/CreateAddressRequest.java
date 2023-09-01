package com.reno.reno.model;

import lombok.Data;

@Data
public class CreateAddressRequest {

    private String tal;

    private boolean isActive;

    private String houseNumber;

    private String subdistrict;

    private String district;

    private String province;

    private Integer postalcode;
}
