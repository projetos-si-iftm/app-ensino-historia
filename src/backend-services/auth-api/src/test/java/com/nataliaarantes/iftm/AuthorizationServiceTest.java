package com.nataliaarantes.iftm;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.nataliaarantes.iftm.model.Classroom;
import com.nataliaarantes.iftm.model.Student;
import com.nataliaarantes.iftm.model.Teacher;
import com.nataliaarantes.iftm.model.User;
import com.nataliaarantes.iftm.model.dto.login.LoginDTO;
import com.nataliaarantes.iftm.model.dto.login.LoginResponseDTO;
import com.nataliaarantes.iftm.model.dto.register.RegisterDTO;
import com.nataliaarantes.iftm.model.dto.register.RegisterResponseDTO;
import com.nataliaarantes.iftm.repository.ClassroomRepository;
import com.nataliaarantes.iftm.repository.UserRepository;
import com.nataliaarantes.iftm.service.AuthorizationService;
import com.nataliaarantes.iftm.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.client.HttpClientErrorException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private ClassroomRepository classroomRepository;

  @Mock
  private TokenService tokenService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private MongoTemplate mongoTemplate;

  @InjectMocks
  private AuthorizationService authorizationService;

  private RegisterDTO registerDTO;
  private LoginDTO loginDTO;

  @BeforeEach
  void setUp() {
    registerDTO = new RegisterDTO("John Doe", "john@example.com", "password123", false, "classId");
    loginDTO = new LoginDTO("john@example.com", "password123");
  }

  @Test
  @DisplayName("Deve conseguir realizar o login")
  void testLoginSuccess() {
    Student mockUser = new Student("John Doe", "john@example.com", "password123", true, "classId");

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(new UsernamePasswordAuthenticationToken(mockUser, null));
    when(tokenService.generateToken(mockUser)).thenReturn("mockToken");

    LoginResponseDTO response = authorizationService.login(loginDTO);

    assertNotNull(response);
    assertEquals("mockToken", response.getToken());
  }

  @Test
  @DisplayName("Deve conseguir se registrar")
  void testRegisterUserSuccess() {
    when(userRepository.findByEmail(registerDTO.getEmail())).thenReturn(Optional.empty());
    when(classroomRepository.findByUuid(registerDTO.getClassId())).thenReturn(Optional.of(new Classroom("classId", "class1", "2025", true)));
    when(userRepository.save(any(User.class)))
        .thenReturn(new Student("John Doe", "john@example.com", "hashedPassword", true, "classId"));

    RegisterResponseDTO response = authorizationService.register(registerDTO);

    assertNotNull(response);
    assertEquals("john@example.com", response.getEmail());
    assertEquals("John Doe", response.getName());
  }

  @Test
  @DisplayName("Deve dar erro ao registrar pois email já existe")
  void testRegisterUserAlreadyExists() {
    when(userRepository.findByEmail(registerDTO.getEmail()))
        .thenReturn(Optional.of(new Student("John Doe", "john@example.com", "hashedPassword", true, "classId")));

    Exception exception = assertThrows(RuntimeException.class, () -> authorizationService.register(registerDTO));
    assertTrue(exception.getMessage().contains("Email already exists"));
  }

  @Test
  @DisplayName("Deve falhar ao registrar professor pois email é inválido")
  void testRegisterTeacherWithInvalidEmail() {
    RegisterDTO teacherDTO = new RegisterDTO("Jane Doe", "jane@invalid.com", "password123", true, "");
    when(userRepository.findByEmail(teacherDTO.getEmail())).thenReturn(Optional.empty());
    when(mongoTemplate.exists(any(), eq("teacher_mails"))).thenReturn(false);

    Exception exception = assertThrows(HttpClientErrorException.class, () -> authorizationService.register(teacherDTO));
    assertTrue(exception.getMessage().contains("This user cannot register as a teacher"));
  }

  @Test
  @DisplayName("Deve se registrar como professor")
  void testRegisterTeacherWithValidEmail() {
    RegisterDTO teacherDTO = new RegisterDTO("Jane Doe", "jane@valid.com", "password123", true, "");
    when(userRepository.findByEmail(teacherDTO.getEmail())).thenReturn(Optional.empty());
    when(mongoTemplate.exists(any(), eq("teacher_mails"))).thenReturn(true);
    when(userRepository.save(any(User.class)))
        .thenReturn(new Teacher("Jane Doe", "jane@valid.com", "hashedPassword", true));

    RegisterResponseDTO response = authorizationService.register(teacherDTO);

    assertNotNull(response);
    assertEquals("jane@valid.com", response.getEmail());
    assertEquals("Jane Doe", response.getName());
  }
}
