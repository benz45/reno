package com.reno.reno.model.auth;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reno.reno.model.base.BaseColumnCreatedUpdatedIsDeleted;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(schema = "e_commerce_info", name = "user", uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
})
@Data
@EqualsAndHashCode(callSuper = false)
public class UserEntity extends BaseColumnCreatedUpdatedIsDeleted {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 50)
  private String username;

  @JsonIgnore
  @NotBlank
  @Size(max = 200)
  private String password;

  @ManyToOne
  @JoinColumn(name = "user_type_id", referencedColumnName = "id")
  private UserTypeEntity userType;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
  @JoinTable(schema = "e_commerce_info", name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_type_id"))
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Set<RoleTypeEntity> roleTypes = new HashSet<>();

}
