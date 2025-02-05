package com.nataliaarantes.iftm.model.dto.classroom;

import com.nataliaarantes.iftm.model.Classroom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassroomResponseDTO {
  private String uuid;
  private String name;
  private String year;
  private boolean isActive;


  public static ClassroomResponseDTO modelToResponseDto(Classroom model) {
    return ClassroomResponseDTO.builder()
        .uuid(model.getUuid())
        .name(model.getName())
        .year(model.getYear())
        .isActive(model.isActive())
        .build();
  }
}
