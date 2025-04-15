package com.iftm.dto.authDTO.dto.register;

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
