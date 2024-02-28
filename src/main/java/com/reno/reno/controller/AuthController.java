package com.reno.reno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.reno.reno.businessflow.UserBusinessFlow;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.user.UserEntity;
import com.reno.reno.model.user.UserSignUpRequest;

import jakarta.validation.Valid;

@RestController
public class AuthController {

	private @Autowired UserBusinessFlow userBusinessFlow;

	@PostMapping("/auth/signup")
	public UserEntity registerUser(@Valid @RequestBody UserSignUpRequest request) throws ApiException {
		UserEntity user = userBusinessFlow.signupUser(request);
		return user;
	}
}
