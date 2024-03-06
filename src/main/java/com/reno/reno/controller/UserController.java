package com.reno.reno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reno.reno.model.auth.UserEntity;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.repository.auth.UserRepository;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/e-commerce-info")
public class UserController {

	private @Autowired UserRepository userRepository;

	@GetMapping("/user/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public UserEntity getUser(@Valid @PathVariable Long id) throws ApiException {

		UserEntity user = userRepository.findById(id)
				.orElseThrow(() -> new ApiException("400", "Can't found user id: " + id));

		return user;
	}

}
