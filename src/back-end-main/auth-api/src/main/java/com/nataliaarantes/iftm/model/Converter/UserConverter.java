package com.nataliaarantes.iftm.model.Converter;

import iftm.edu.br.Dto.Autenticacao.register.RegisterDTO;
import iftm.edu.br.Dto.Autenticacao.register.RegisterResponseDTO;
import iftm.edu.br.model.Student;
import iftm.edu.br.model.Teacher;
import iftm.edu.br.model.User;

public class UserConverter {

    public static RegisterResponseDTO toRegisterResponseDto(User user) {
        return RegisterResponseDTO.builder()
                .uuid(user.getUuid())
                .isTeacher(user instanceof Teacher)
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public static User fromRegisterDto(RegisterDTO dto) {
        if (dto.isTeacher()) {
            return createTeacher(dto);
        } else {
            return createStudent(dto);
        }
    }

    // Método auxiliar para criar um Teacher
    private static Teacher createTeacher(RegisterDTO dto) {
        Teacher teacher = new Teacher(null, null, null, null);
        populateCommonFields(teacher, dto);
        return teacher;
    }

    // Método auxiliar para criar um Student
    private static Student createStudent(RegisterDTO dto) {
        Student student = new Student();
        populateCommonFields(student, dto);
        student.setClassId(dto.getClassId()); // Campo específico de Student
        return student;
    }

    // Método auxiliar para preencher campos comuns
    private static void populateCommonFields(User user, RegisterDTO dto) {
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setActive(true);
    }
}