package com.reno.reno.model;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CreateAddressRequest {

    @NotNull(message = "Address tal required.")
    private String tal;

    private boolean isActive;

    private String houseNumber;

    private String subdistrict;

    private String district;

    private String province;

    @NotNull(message = "Address postalcode required.")
    private Integer postalcode;
}
