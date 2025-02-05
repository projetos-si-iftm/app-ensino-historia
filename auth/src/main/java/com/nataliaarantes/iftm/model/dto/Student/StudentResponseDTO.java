package com.nataliaarantes.iftm.model.dto.Student;

import com.nataliaarantes.iftm.model.Student;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class StudentResponseDTO {
  private String uuid;
  private String name;
  private String email;
  private boolean isActive;

  public static StudentResponseDTO modelToDto(Student model) {
    return StudentResponseDTO.builder()
        .uuid(model.getUuid())
        .name(model.getName())
        .email(model.getEmail())
        .isActive(model.isActive())
        .build();
  }
}

