package com.reno.reno.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public TokenRefreshException(String token, String message) {
    super("Failed for [%s]: %s".formatted(token, message));
  }
}
