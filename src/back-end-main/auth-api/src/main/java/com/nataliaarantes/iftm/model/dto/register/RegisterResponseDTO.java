package com.nataliaarantes.iftm.model.dto.register;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDTO {
  private String uuid;
  private boolean isTeacher;
  private String email;
  private String name;
}
