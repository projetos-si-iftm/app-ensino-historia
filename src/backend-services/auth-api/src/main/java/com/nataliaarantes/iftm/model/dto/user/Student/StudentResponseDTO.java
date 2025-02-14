package com.nataliaarantes.iftm.model.dto.user.Student;

import com.nataliaarantes.iftm.model.Student;
import com.nataliaarantes.iftm.model.dto.user.UserResponseDTO;
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

