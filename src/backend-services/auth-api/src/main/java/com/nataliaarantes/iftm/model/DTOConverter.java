package com.iftm.converter;

import com.iftm.dto.classroom.ClassroomDTO;
import com.iftm.dto.classroom.ClassroomResponseDTO;
import com.iftm.dto.login.LoginDTO;
import com.iftm.dto.register.RegisterDTO;
import com.iftm.dto.register.RegisterResponseDTO;
import com.iftm.dto.token.ValidateTokenDTO;
import com.iftm.dto.user.StudentResponseDTO;
import com.iftm.dto.user.TeacherResponseDTO;
import com.iftm.dto.user.UserDTO;
import com.iftm.dto.user.UserResponseDTO;
import com.ensinohistoria.models.DTO.EstatisticasDTO;
import com.ensinohistoria.models.DTO.RespostasDTO;

import java.util.stream.Collectors;

public class DTOConverter {

    public static ClassroomDTO toClassroomDTO(Classroom classroom) {
        return new ClassroomDTO(
                classroom.getName(),
                classroom.getYear()
        );
    }

    public static ClassroomResponseDTO toClassroomResponseDTO(Classroom classroom) {
        return ClassroomResponseDTO.builder()
                .uuid(classroom.getUuid())
                .name(classroom.getName())
                .year(classroom.getYear())
                .isActive(classroom.isActive())
                .students(classroom.getStudents().stream()
                        .map(DTOConverter::toStudentResponseDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public static StudentResponseDTO toStudentResponseDTO(Student student) {
        return new StudentResponseDTO(
                student.getUuid(),
                student.getName(),
                student.getEmail(),
                student.isActive(),
                student.getClassId()
        );
    }  

    public static TeacherResponseDTO toTeacherResponseDTO(Teacher teacher) {
        return new TeacherResponseDTO(
                teacher.getUuid(),
                teacher.getName(),
                teacher.getEmail(),
                teacher.isActive()
        );
    }

    public static UserDTO toUserDTO(String email, String password, String name, String classId) {
        return new UserDTO(email, password, name, classId);
    }

    public static UserResponseDTO toUserResponseDTO(String uuid, String name, String email, boolean isActive) {
        return new UserResponseDTO(uuid, name, email, isActive);
    }

    public static LoginDTO toLoginDTO(String email, String password) {
        return new LoginDTO(email, password);
    }

    public static RegisterDTO toRegisterDTO(String name, String email, String password, boolean isTeacher, String classId) {
        return new RegisterDTO(name, email, password, isTeacher, classId);
    }

    public static RegisterResponseDTO toRegisterResponseDTO(String uuid, boolean isTeacher, String email, String name) {
        return new RegisterResponseDTO(uuid, isTeacher, email, name);
    }

    public static ValidateTokenDTO toValidateTokenDTO(String token) {
        return new ValidateTokenDTO(token);
    }

    public static EstatisticasDTO toEstatisticasDTO(Long totalRespostas, Long respostasCorretas, Long respostasIncorretas) {
        return new EstatisticasDTO(totalRespostas, respostasCorretas, respostasIncorretas);
    }

    public static RespostasDTO toRespostasDTO(String id, java.util.UUID alunoId, java.util.UUID questaoId, java.util.UUID alternativaId, java.time.LocalDateTime dataInicio, java.time.LocalDateTime dataFim, java.time.LocalDateTime dataCriacao, java.time.LocalDateTime dataAtualizacao, boolean ativo) {
        return new RespostasDTO(id, alunoId, questaoId, alternativaId, dataInicio, dataFim, dataCriacao, dataAtualizacao, ativo);
    }
}