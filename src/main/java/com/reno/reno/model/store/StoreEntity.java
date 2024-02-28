package com.reno.reno.model.store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Where;

import com.reno.reno.model.AddressEntiry;
import com.reno.reno.model.base.BaseColumnCreatedUpdatedIsDeleted;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "store", schema = "e_commerce_info")
@Where(clause = "is_deleted = false")
@EqualsAndHashCode(callSuper = false)
@Data
public class StoreEntity extends BaseColumnCreatedUpdatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "detail")
    private String detail;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntiry address;

}
