package iftm.edu.br.Dto.Autenticacao.user.Student;

import iftm.edu.br.model.Student;
import iftm.edu.br.Dto.Autenticacao.user.UserResponseDTO;
import lombok.*;

@Getter
@Setter
public class StudentResponseDTO extends UserResponseDTO {
  public StudentResponseDTO(String uuid, String name, String email, boolean isActive, String classId) {
    super(uuid, name, email, isActive);
    this.classId = classId;
  }

  private String classId;

  public static StudentResponseDTO modelToDto(Student model) {
    return new StudentResponseDTO(
        model.getUuid(),
        model.getName(),
        model.getEmail(),
        model.isActive(),
        model.getClassId()
    );
  }
}

