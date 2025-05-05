package iftm.edu.br.Dto.Autenticacao.register;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
  private String name;
  private String email;
  private String password;
  private boolean isTeacher;
  private String classId;
}
