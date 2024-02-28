package com.reno.reno.model.employee;

import java.util.Date;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reno.reno.model.GenderEntity;

import lombok.Data;

@Data
public class EmployeeRequest {
    @NotNull(message = "username required.")
    private String username;

    @NotNull(message = "password required.")
    private String password;

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
