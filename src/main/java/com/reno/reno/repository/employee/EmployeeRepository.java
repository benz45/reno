package com.reno.reno.repository.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.employee.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    Boolean existsByEmail(String email);
}
