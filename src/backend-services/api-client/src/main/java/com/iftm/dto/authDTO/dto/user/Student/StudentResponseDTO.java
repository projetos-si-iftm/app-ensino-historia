package com.iftm.dto.authDTO.dto.user.Student;

import lombok.*;
import com.iftm.dto.authDTO.dto.user.UserResponseDTO;

@Getter
@Setter
public class StudentResponseDTO extends UserResponseDTO {
  public StudentResponseDTO(String uuid, String name, String email, boolean isActive, String classId) {
    super(uuid, name, email, isActive);
    this.classId = classId;
  }

  private String classId;
}
