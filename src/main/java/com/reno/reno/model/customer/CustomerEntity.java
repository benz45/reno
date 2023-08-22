package com.reno.reno.model.customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.reno.reno.model.base.BaseColumnCreatedUpdatedIsDeleted;

@Entity
@Table(name = "customer", schema = "ecommerce_store")
@Where(clause = "is_deleted = false")
public class CustomerEntity extends BaseColumnCreatedUpdatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "tal")
    private Integer tal;

    @ManyToOne
    @JoinColumn(name = "customer_leval_id", referencedColumnName = "id")
    private CustomerLevalEntity customerLevalEntity;
}
