package com.reno.reno.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.GenderEntity;

public interface GenderRepository extends JpaRepository<GenderEntity, Integer> {

}
