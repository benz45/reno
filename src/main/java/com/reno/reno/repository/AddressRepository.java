package com.reno.reno.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.AddressEntiry;

public interface AddressRepository extends JpaRepository<AddressEntiry, Long> {

}
