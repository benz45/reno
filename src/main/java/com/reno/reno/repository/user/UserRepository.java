package com.reno.reno.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.user.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
