package com.reno.reno.model.auth;

import com.reno.reno.constant.UserTypeConstant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(schema = "e_commerce_info", name = "user_type")
public class UserTypeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(length = 50)
  private UserTypeConstant nameTh;

  @Enumerated(EnumType.STRING)
  @Column(length = 50)
  private UserTypeConstant nameEn;

}