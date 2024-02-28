package com.reno.reno.model.user;

import java.util.List;

import com.reno.reno.model.role.RoleTypeEntity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserSignUpRequest {
    @NotNull
    private String name;
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private List<RoleTypeEntity> roleTypes;
}
