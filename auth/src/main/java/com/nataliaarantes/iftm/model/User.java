package com.nataliaarantes.iftm.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

@Data
@NoArgsConstructor
@Document(collection = "users")
public abstract class User implements UserDetails {

  @Id
  private String uuid;

  @Field("name")
  private String name;

  @Field("email")
  @Indexed(unique = true)
  private String email;

  @Field("password")
  private String password;

  @Field("is_active")
  private boolean isActive;

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.email;
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
    return this.isActive;
  }
}