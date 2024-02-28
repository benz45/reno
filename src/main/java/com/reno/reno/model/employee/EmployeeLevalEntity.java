package com.reno.reno.model.employee;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.reno.reno.model.base.BaseNameThNameEn;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "employee_leval", schema = "e_commerce_info")
@Data
@EqualsAndHashCode(callSuper = false)
public class EmployeeLevalEntity extends BaseNameThNameEn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

}
