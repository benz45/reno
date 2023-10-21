package com.reno.reno.model.customer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reno.reno.model.GenderEntity;
import com.reno.reno.model.base.BaseColumnCreatedUpdatedIsDeleted;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "customer", schema = "ecommerce_store")
@Where(clause = "is_deleted = false")
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerEntity extends BaseColumnCreatedUpdatedIsDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "tal")
    private String tal;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthday")
    private Date birthday;

    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private GenderEntity gender;

    @ManyToOne
    @JoinColumn(name = "customer_leval_id", referencedColumnName = "id")
    private CustomerLevalEntity customerLeval;
}
