package com.reno.reno.model.store;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Where;

import com.reno.reno.model.base.BaseColumnCreatedIsDeleted;
import com.reno.reno.model.customer.CustomerEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "store_owner", schema = "e_commerce_info")
@Where(clause = "is_deleted = false")
@EqualsAndHashCode(callSuper = false)
@Data
public class StoreOwnerEntity extends BaseColumnCreatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private StoreEntity store;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerEntity customer;

}
