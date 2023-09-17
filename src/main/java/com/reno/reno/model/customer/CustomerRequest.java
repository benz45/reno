package com.reno.reno.model.customer;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reno.reno.model.GenderEntity;

import lombok.Data;

@Data
public class CustomerRequest {
    @NotNull(message = "username required.")
    private String username;
    @NotNull(message = "name required.")
    private String name;

    @NotNull(message = "email required.")
    private String email;

    @NotNull(message = "gender required.")
    private GenderEntity gender;

    @NotNull(message = "tal required.")
    private String tal;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
}