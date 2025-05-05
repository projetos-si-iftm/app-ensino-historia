package com.nataliaarantes.iftm.model.Converter;

import iftm.edu.br.Dto.Autenticacao.user.Student.StudentResponseDTO;
import iftm.edu.br.model.Student;
import iftm.edu.br.Dto.Autenticacao.user.UserDTO;

public class StudentConverter {
    
    public static Student toEntity(UserDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setPassword(dto.getPassword());
        student.setClassId(dto.getClassId());
        student.setActive(true);
        return student;
    }
    
    public static UserDTO toDto(Student entity) {
        return UserDTO.builder()
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .classId(entity.getClassId())
                .build();
    }
    
    public static StudentResponseDTO toResponseDto(Student entity) {
        return new StudentResponseDTO(
                entity.getUuid(),
                entity.getName(),
                entity.getEmail(),
                entity.isActive(),
                entity.getClassId()
        );
    }
}