package com.reno.reno.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.auth.UserRoleEntity;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
}
