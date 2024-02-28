package com.reno.reno.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.role.RoleTypeEntity;

public interface RoleTypeRepository extends JpaRepository<RoleTypeEntity, Integer> {
}
