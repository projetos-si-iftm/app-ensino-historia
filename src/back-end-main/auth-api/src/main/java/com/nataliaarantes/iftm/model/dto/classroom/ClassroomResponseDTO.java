package com.nataliaarantes.iftm.model.dto.classroom;

import com.nataliaarantes.iftm.model.Classroom;
import com.nataliaarantes.iftm.model.dto.user.Student.StudentResponseDTO;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class ClassroomResponseDTO {
  private String uuid;
  private String name;
  private String year;
  private boolean isActive;
  private List<StudentResponseDTO> students;


  public static ClassroomResponseDTO modelToResponseDto(Classroom model) {
    return ClassroomResponseDTO.builder()
        .uuid(model.getUuid())
        .name(model.getName())
        .year(model.getYear())
        .isActive(model.isActive())
        .build();
  }
}
