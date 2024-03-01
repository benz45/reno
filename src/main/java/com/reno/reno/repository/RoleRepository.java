package com.reno.reno.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.reno.reno.model.ERole;
import com.reno.reno.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
