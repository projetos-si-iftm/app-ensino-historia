package com.nataliaarantes.iftm.service;

import com.nataliaarantes.iftm.model.Student;
import com.nataliaarantes.iftm.model.Teacher;
import com.nataliaarantes.iftm.model.User;
import com.nataliaarantes.iftm.model.dto.user.UserDTO;
import com.nataliaarantes.iftm.model.dto.user.UserResponseDTO;
import com.nataliaarantes.iftm.model.dto.user.Student.StudentResponseDTO;
import com.nataliaarantes.iftm.model.dto.user.Teacher.TeacherResponseDTO;
import com.nataliaarantes.iftm.repository.ClassroomRepository;
import com.nataliaarantes.iftm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  ClassroomRepository classroomRepository;

  @Autowired
  TokenService tokenService;


  public UserResponseDTO update(UserDTO dto, String token) {
    String email = tokenService.validateToken(token.replace("Bearer ", ""));
    if(Objects.equals(email, "")) {
      throw new HttpClientErrorException(HttpStatusCode.valueOf(500), "INTERNAL ERROR");
    }

    User user = userRepository.findByEmail(email).orElseThrow(RuntimeException::new);
    boolean isStudent = user.getClass() == Student.class;

    if(dto.getName() != null) {
      user.setName(dto.getName());
    }

    if(dto.getEmail() != null) {
      Optional<User> byEmail = userRepository.findByEmail(dto.getEmail());

      if(byEmail.isPresent()) {
        throw new HttpClientErrorException(HttpStatusCode.valueOf(400), "Email already exists");
      }

      user.setEmail(dto.getEmail());
    }

    if(dto.getPassword() != null) {
      String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
      user.setPassword(encryptedPassword);
    }

    if(dto.getClassId() != null && isStudent) {
      classroomRepository.findByUuid(dto.getClassId()).orElseThrow(() -> new HttpClientErrorException(HttpStatusCode.valueOf(400), "Invalid classroom"));
      ((Student) user).setClassId(dto.getClassId());
    }

    user = userRepository.save(user);
    return isStudent ? StudentResponseDTO.modelToDto((Student) user) : TeacherResponseDTO.modelToDto((Teacher) user);
  }
}
