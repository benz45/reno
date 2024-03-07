package com.reno.reno.payload.request;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reno.reno.model.GenderEntity;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignupRequest {
  @NotNull(message = "name required.")
  @Size(min = 3, max = 20)
  private String name;

  @NotNull(message = "username required.")
  @Size(min = 3, max = 20)
  private String username;

  @NotNull(message = "password required.")
  @Size(min = 6, max = 40)
  private String password;

  @NotNull(message = "user type required.")
  private Integer userTypeId;

  @NotNull(message = "email required.")
  @Size(max = 50)
  @Email
  private String email;

  private Set<Integer> role;

  @NotNull(message = "gender required.")
  private GenderEntity gender;

  @NotNull(message = "tal required.")
  @Size(max = 10)
  private String tal;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date birthday;
}
