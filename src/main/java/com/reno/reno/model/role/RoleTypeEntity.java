package com.reno.reno.model.role;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "role_type", schema = "e_commerce_info")
@EqualsAndHashCode(callSuper = false)
public class RoleTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name = "name_th")
    private RoleName nameTh;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name = "name_en")
    private RoleName nameEn;

}
