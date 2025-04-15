package com.iftm.dto.authDTO.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserResponseDTO {
  private String uuid;
  private String name;
  private String email;
  private boolean isActive;

}
