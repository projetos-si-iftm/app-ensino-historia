package com.nataliaarantes.iftm.model;

import com.nataliaarantes.iftm.model.dto.classroom.ClassroomDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "classroms")
public class Classroom {
  @Id
  private String uuid;

  @Field("name")
  private String name;

  @Field("year")
  private String year;

  @Field("is_active")
  private boolean isActive;


  public static Classroom dtoToMode(ClassroomDTO dto) {
    return Classroom.builder()
        .name(dto.getName())
        .year(dto.getYear())
        .isActive(true)
        .build();
  }
}
