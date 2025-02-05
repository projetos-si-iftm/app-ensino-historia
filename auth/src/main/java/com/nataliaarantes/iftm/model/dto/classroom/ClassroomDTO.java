package com.nataliaarantes.iftm.model.dto.classroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassroomDTO {
  private String name;
  private String year;
}
