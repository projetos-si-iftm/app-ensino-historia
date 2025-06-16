package com.nataliaarantes.iftm.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
@Getter
@Setter
public class Teacher extends User {

  public Teacher(String name, String email, String password, Boolean isActive) {
    super(name, email, password, isActive);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_TEACHER"));
  }
}