package com.reno.reno.repository.auth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.auth.UserTypeEntity;

public interface UserTypeRepository extends JpaRepository<UserTypeEntity, Integer> {
  Optional<UserTypeEntity> findById(Integer id);

}
