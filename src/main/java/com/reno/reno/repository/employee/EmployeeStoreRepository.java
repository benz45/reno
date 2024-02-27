package com.reno.reno.repository.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.employee.EmployeeStoreEntity;

public interface EmployeeStoreRepository extends JpaRepository<EmployeeStoreEntity, Long> {

}
