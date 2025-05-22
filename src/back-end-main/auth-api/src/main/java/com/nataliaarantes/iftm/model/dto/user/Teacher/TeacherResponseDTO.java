package com.nataliaarantes.iftm.model.dto.user.Teacher;

import com.nataliaarantes.iftm.model.Teacher;
import com.nataliaarantes.iftm.model.dto.user.UserResponseDTO;

public class TeacherResponseDTO extends UserResponseDTO {
  public TeacherResponseDTO(String uuid, String name, String email, boolean isActive) {
    super(uuid, name, email, isActive);
  }

  public static TeacherResponseDTO modelToDto(Teacher model) {
    return new TeacherResponseDTO(
        model.getUuid(),
        model.getName(),
        model.getEmail(),
        model.isActive()
    );
  }
}
