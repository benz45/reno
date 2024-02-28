package com.reno.reno.model.employee;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Where;

import com.reno.reno.model.base.BaseColumnCreatedIsDeleted;
import com.reno.reno.model.store.StoreEntity;

@Entity
@Table(name = "employee_store", schema = "e_commerce_info")
@Where(clause = "is_deleted = false")
public class EmployeeStoreEntity extends BaseColumnCreatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private EmployeeEntity employee;

    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private StoreEntity store;

}
