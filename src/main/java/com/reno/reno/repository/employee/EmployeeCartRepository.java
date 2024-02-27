package com.reno.reno.repository.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reno.reno.model.employee.EmployeeCartEntity;

public interface EmployeeCartRepository extends JpaRepository<EmployeeCartEntity, Long> {

}
