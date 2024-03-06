package com.reno.reno.repository.auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.constant.RoleTypeConstant;
import com.reno.reno.model.auth.RoleTypeEntity;

public interface RoleRepository extends JpaRepository<RoleTypeEntity, Integer> {
  Optional<RoleTypeEntity> findById(Integer id);

  Optional<RoleTypeEntity> findByNameEn(RoleTypeConstant name);

}
