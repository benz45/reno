package com.reno.reno.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.ImageEntity;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

}
