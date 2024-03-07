package com.reno.reno.payload.response;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {
  private String accessToken;
  private String refreshToken;
  private String type = "Bearer";
  private Long id;
  private String username;
  private List<String> roles;
}
