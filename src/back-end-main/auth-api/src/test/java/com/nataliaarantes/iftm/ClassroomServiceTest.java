package com.nataliaarantes.iftm;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import iftm.edu.br.Dto.Autenticacao.classroom.ClassroomDTO;
import iftm.edu.br.Dto.Autenticacao.classroom.ClassroomResponseDTO;
import iftm.edu.br.model.Classroom;
import iftm.edu.br.model.Student;
import iftm.edu.br.model.Teacher;

import com.nataliaarantes.iftm.repository.ClassroomRepository;
import com.nataliaarantes.iftm.repository.UserRepository;
import com.nataliaarantes.iftm.service.ClassroomService;
import com.nataliaarantes.iftm.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClassroomServiceTest {

  @Mock
  private ClassroomRepository classroomRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private TokenService tokenService;

  @InjectMocks
  private ClassroomService classroomService;

  private ClassroomDTO classroomDTO;
  private Classroom classroom;
  private Teacher teacher;
  private Student student;

  private final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwaSIsInN1YiI6InByb2ZAaWZ0bS5jb20iLCJ1dWlkIjoiNjdiMzQyOGUzNjQ0ZDk3ODFkMjZhZGQxIiwidHlwZSI6InRlYWNoZXIiLCJjbGFzc0lkIjoiIiwiZXhwIjoxNzcxMzQ0MDc0fQ.Qe6vO0DnNXnVtj3uwe-spLXKG0nmIcIChikAOTyTZwA";

  @BeforeEach
  void setUp() {
    classroomDTO = new ClassroomDTO("Math", "2025");
    classroom = new Classroom("1", "Math", "2025", true);
    teacher = new Teacher("Teacher Name", "teacher@example.com", "password", true);
    student = new Student("Student Name", "student@example.com", "password", true, "1");
  }

  @Test
  @DisplayName("Deve criar turma com acesso de Professor")
  void testCreateClassroomAsTeacher() {
    when(classroomRepository.findByName(classroomDTO.getName())).thenReturn(Optional.empty());
    when(classroomRepository.save(any(Classroom.class))).thenReturn(classroom);
    when(tokenService.validateToken(token)).thenReturn(teacher.getEmail());
    when(userRepository.findByEmail(teacher.getEmail())).thenReturn(Optional.ofNullable(teacher));

    ClassroomResponseDTO response = classroomService.create(classroomDTO, token);

    assertNotNull(response);
    assertEquals("Math", response.getName());
  }

  @Test
  @DisplayName("Deve falhar ao criar turma pois já existe")
  void testCreateClassroomAlreadyExists() {
    when(classroomRepository.findByName(classroomDTO.getName())).thenReturn(Optional.of(classroom));

    Exception exception = assertThrows(HttpClientErrorException.class, () -> classroomService.create(classroomDTO, token));
    assertTrue(exception.getMessage().contains("Classroom already exists"));
  }

  @Test
  @DisplayName("Deve bsucar turma por ID")
  void testFindByUuidSuccess() {
    when(classroomRepository.findByUuid("1")).thenReturn(Optional.of(classroom));

    ClassroomResponseDTO response = classroomService.findByUuid("1");

    assertNotNull(response);
    assertEquals("Math", response.getName());
  }

  @Test
  @DisplayName("Deve falhar ao buscar turma")
  void testFindByUuidNotFound() {
    when(classroomRepository.findByUuid("1")).thenReturn(Optional.empty());

    Exception exception = assertThrows(HttpClientErrorException.class, () -> classroomService.findByUuid("1"));
    assertTrue(exception.getMessage().contains("Class not found"));
  }

  @Test
  @DisplayName("Deve buscar todas as turmas")
  void testFindAll() {
    when(classroomRepository.findAll()).thenReturn(List.of(classroom));

    List<ClassroomResponseDTO> response = classroomService.findAll();

    assertFalse(response.isEmpty());
    assertEquals(1, response.size());
    assertEquals("Math", response.get(0).getName());
  }

  @Test
  @DisplayName("Deve atualizar turma com acesso de Professor")
  void testUpdateClassroomAsTeacher() {
    when(classroomRepository.findByUuid("1")).thenReturn(Optional.of(classroom));
    when(classroomRepository.save(any(Classroom.class))).thenReturn(classroom);
    when(tokenService.validateToken(token)).thenReturn(teacher.getEmail());
    when(userRepository.findByEmail(teacher.getEmail())).thenReturn(Optional.ofNullable(teacher));

    ClassroomResponseDTO response = classroomService.update("1", classroomDTO, token);

    assertNotNull(response);
    assertEquals("Math", response.getName());
  }

  @Test
  @DisplayName("Deve falhar ao atualizar turma pois não encontra com o id fornecido")
  void testUpdateClassroomNotFound() {
    when(classroomRepository.findByUuid("1")).thenReturn(Optional.empty());

    Exception exception = assertThrows(HttpClientErrorException.class, () -> classroomService.update("1", classroomDTO, token));
    assertTrue(exception.getMessage().contains("Class not found"));
  }

  @Test
  @DisplayName("Deve deletar turma com acesso de Professor")
  void testDeleteClassroomAsTeacher() {
    when(classroomRepository.findByUuid("1")).thenReturn(Optional.of(classroom));
    doNothing().when(classroomRepository).delete(any(Classroom.class));
    when(tokenService.validateToken(token)).thenReturn(teacher.getEmail());
    when(userRepository.findByEmail(teacher.getEmail())).thenReturn(Optional.ofNullable(teacher));

    assertDoesNotThrow(() -> classroomService.delete("1", token));
  }

  @Test
  @DisplayName("Deve deletar turma com acesso de Professor")
  void testDeleteClassroomNotFound() {
    when(classroomRepository.findByUuid("1")).thenReturn(Optional.empty());

    Exception exception = assertThrows(HttpClientErrorException.class, () -> classroomService.delete("1", token));
    assertTrue(exception.getMessage().contains("Class not found"));
  }
}
