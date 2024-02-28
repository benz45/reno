package com.reno.reno.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.role.UserRoleEntity;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
}
