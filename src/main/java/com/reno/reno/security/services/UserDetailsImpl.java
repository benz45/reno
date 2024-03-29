package com.reno.reno.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reno.reno.model.auth.UserEntity;
import com.reno.reno.model.auth.UserTypeEntity;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;

  private String username;

  private UserTypeEntity userType;

  @JsonIgnore
  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(Long id, String username, String password, UserTypeEntity userType,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.userType = userType;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(UserEntity user) {
    List<GrantedAuthority> authorities = user.getRoleTypes().stream()
        .map(role -> new SimpleGrantedAuthority(role.getNameEn().name()))
        .collect(Collectors.toList());
    return new UserDetailsImpl(user.getId(),
        user.getUsername(),
        user.getPassword(),
        user.getUserType(),
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public UserTypeEntity getUserType() {
    return userType;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
