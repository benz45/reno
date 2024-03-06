package com.reno.reno.model.auth;

import com.reno.reno.model.base.BaseColumnCreatedUpdatedIsDeleted;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "e_commerce_info", name = "user_role")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserRoleEntity extends BaseColumnCreatedUpdatedIsDeleted {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "role_type_id", referencedColumnName = "id")
  private RoleTypeEntity roleType;
}
