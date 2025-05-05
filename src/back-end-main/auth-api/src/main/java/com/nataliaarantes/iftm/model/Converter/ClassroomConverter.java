package com.nataliaarantes.iftm.model.Converter;

import iftm.edu.br.Dto.Autenticacao.classroom.ClassroomDTO;
import iftm.edu.br.Dto.Autenticacao.classroom.ClassroomResponseDTO;
import iftm.edu.br.Dto.Autenticacao.user.Student.StudentResponseDTO;
import iftm.edu.br.model.Classroom;
import iftm.edu.br.model.Student;

import java.util.List;
import java.util.stream.Collectors;

public class ClassroomConverter {
    
    public static Classroom toEntity(ClassroomDTO dto) {
        return Classroom.builder()
                .name(dto.getName())
                .year(dto.getYear())
                .build();
    }
    
    public static ClassroomDTO toDto(Classroom entity) {
        return ClassroomDTO.builder()
                .name(entity.getName())
                .year(entity.getYear())
                .build();
    }
    
    public static ClassroomResponseDTO toResponseDto(Classroom entity) {
        return ClassroomResponseDTO.builder()
                .uuid(entity.getUuid())
                .name(entity.getName())
                .year(entity.getYear())
                .isActive(entity.isActive())
                .build();
    }
    
    public static ClassroomResponseDTO toResponseDtoWithStudents(Classroom entity, List<Student> students) {
        List<StudentResponseDTO> studentDtos = students.stream()
                .map(StudentConverter::toResponseDto)
                .collect(Collectors.toList());
                
        return ClassroomResponseDTO.builder()
                .uuid(entity.getUuid())
                .name(entity.getName())
                .year(entity.getYear())
                .isActive(entity.isActive())
                .students(studentDtos)
                .build();
    }
}