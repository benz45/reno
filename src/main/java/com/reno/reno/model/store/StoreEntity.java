package com.reno.reno.model.store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.reno.reno.model.AddressEntiry;
import com.reno.reno.model.base.BaseColumnCreatedUpdatedIsDeleted;

@Entity
@Table(name = "store", schema = "ecommerce_store")
@Where(clause = "is_deleted = false")
public class StoreEntity extends BaseColumnCreatedUpdatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_name")
    private String name;

    @Column(name = "detail")
    private String detail;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntiry address;

}
