package com.iftm.dto.authDTO.dto.user.Teacher;

import com.iftm.dto.authDTO.dto.user.UserResponseDTO;

public class TeacherResponseDTO extends UserResponseDTO {
  public TeacherResponseDTO(String uuid, String name, String email, boolean isActive) {
    super(uuid, name, email, isActive);
  }

}
