package com.reno.reno.model.auth;

import com.reno.reno.constant.RoleTypeConstant;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(schema = "e_commerce_info", name = "role_type")
public class RoleTypeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(length = 50)
  private RoleTypeConstant nameTh;

  @Enumerated(EnumType.STRING)
  @Column(length = 50)
  private RoleTypeConstant nameEn;

}