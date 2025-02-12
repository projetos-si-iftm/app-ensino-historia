package com.nataliaarantes.iftm.model.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {
  private String email;
  private String password;
}
