package com.iftm.dto.authDTO.dto.classroom;

import com.iftm.dto.authDTO.dto.user.Student.StudentResponseDTO;
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

}
