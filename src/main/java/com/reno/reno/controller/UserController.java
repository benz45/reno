package com.reno.reno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reno.reno.model.User;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.repository.RoleRepository;
import com.reno.reno.repository.UserRepository;
import com.reno.reno.security.jwt.JwtUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/test")
public class UserController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@GetMapping("/{id}")
	public User getUser(@Valid @PathVariable Long id) throws ApiException {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new ApiException("400", "Can't found user id: " + id));

		return user;
	}

}
