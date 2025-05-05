package com.nataliaarantes.iftm.model.Converter;


import iftm.edu.br.Dto.Autenticacao.user.Teacher.TeacherResponseDTO;
import iftm.edu.br.model.Teacher;
import iftm.edu.br.Dto.Autenticacao.user.UserDTO;

public class TeacherConverter {
    
    public static Teacher toEntity(UserDTO dto) {
        Teacher teacher = new Teacher(null, null, null, null);
        teacher.setName(dto.getName());
        teacher.setEmail(dto.getEmail());
        teacher.setPassword(dto.getPassword());
        teacher.setActive(true);
        return teacher;
    }
    
    public static UserDTO toDto(Teacher entity) {
        return UserDTO.builder()
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();
    }
    
    public static TeacherResponseDTO toResponseDto(Teacher entity) {
        return new TeacherResponseDTO(
                entity.getUuid(),
                entity.getName(),
                entity.getEmail(),
                entity.isActive()
        );
    }
}