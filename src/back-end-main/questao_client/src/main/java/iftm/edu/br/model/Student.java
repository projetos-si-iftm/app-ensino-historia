package iftm.edu.br.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
@Getter
@Setter
public class Student extends User {


  // Construtor padrão
  public Student() {
    super("defaultName", "defaultEmail", "defaultPassword", true);
}
  
  public Student(String name, String email, String password, Boolean isActive, String classId) {
    super(name, email, password, isActive);
    this.classId = classId;
  }

  // Construtor com id e name
  public Student(String id) {
    super("defaultName", "defaultEmail", "defaultPassword", true);
  }


  @Field("class_id")
  private String classId;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }

  
}