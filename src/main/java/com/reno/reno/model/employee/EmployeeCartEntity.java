package com.reno.reno.model.employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Where;

import com.reno.reno.model.base.BaseColumnCreatedUpdatedIsDeleted;
import com.reno.reno.model.product.ProductEntity;

@Entity
@Table(name = "employee_cart", schema = "e_commerce_info")
@Where(clause = "is_deleted = false")
public class EmployeeCartEntity extends BaseColumnCreatedUpdatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private EmployeeEntity employee;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity product;

    @Column(name = "is_check_out")
    private Boolean isCheckOut = false;

}
