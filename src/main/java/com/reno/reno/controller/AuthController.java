package com.reno.reno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reno.reno.business.AuthBusiness;
import com.reno.reno.model.auth.TokenRefreshRequest;
import com.reno.reno.payload.request.SigninRequest;
import com.reno.reno.payload.request.SignupRequest;
import com.reno.reno.payload.response.JwtResponse;
import com.reno.reno.payload.response.MessageResponse;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private @Autowired AuthBusiness authBusiness;

	@PostMapping("/signup")
	public ResponseEntity<MessageResponse> signupUser(@Valid @RequestBody SignupRequest signUpRequest)
			throws Exception {
		return authBusiness.signupUser(signUpRequest);
	}

	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> signin(@Valid @RequestBody SigninRequest loginRequest) {
		return authBusiness.signinUser(loginRequest);
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		return authBusiness.getRefreshToken(request);
	}

	@PostMapping("/signout")
	public ResponseEntity<?> signoutUser(@Valid @RequestBody TokenRefreshRequest request) {
		return authBusiness.signoutUser(request);
	}
}
