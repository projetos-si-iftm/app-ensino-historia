package com.nataliaarantes.iftm.service;

import com.nataliaarantes.iftm.model.Student;
import com.nataliaarantes.iftm.model.Teacher;
import com.nataliaarantes.iftm.model.User;
import com.nataliaarantes.iftm.model.dto.login.LoginDTO;
import com.nataliaarantes.iftm.model.dto.login.LoginResponseDTO;
import com.nataliaarantes.iftm.model.dto.register.RegisterDTO;
import com.nataliaarantes.iftm.model.dto.register.RegisterResponseDTO;
import com.nataliaarantes.iftm.repository.ClassroomRepository;
import com.nataliaarantes.iftm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
public class AuthorizationService {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private TokenService tokenService;

  @Autowired
  UserRepository userRepository;

  @Autowired
  private ClassroomRepository classroomRepository;


  public LoginResponseDTO login(LoginDTO loginDTO) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
    var auth = authenticationManager.authenticate(usernamePassword);
    var token = tokenService.generateToken((User) auth.getPrincipal());

    return LoginResponseDTO.builder()
        .token(token)
        .build();
  }

  public RegisterResponseDTO register(RegisterDTO registerDTO) {
    Optional<User> user = userRepository.findByEmail(registerDTO.getEmail());
    if (user.isPresent()) {
      throw new HttpClientErrorException(HttpStatusCode.valueOf(400), "Email already exists");
    }

    String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.getPassword());
    User userObj = initializeUser(registerDTO.withPassword(encryptedPassword));

    User save = userRepository.save(userObj);
    return RegisterResponseDTO.builder()
        .uuid(save.getUuid())
        .email(save.getEmail())
        .name(save.getName())
        .isTeacher(registerDTO.isTeacher())
        .build();
  }

  private User initializeUser(RegisterDTO registerDTO) {
    if (registerDTO.isTeacher()) {
      isValidTeacherEmail(registerDTO.getEmail());
      return new Teacher(
          registerDTO.getName(),
          registerDTO.getEmail(),
          registerDTO.getPassword(),
          true
      );
    }

    verifyClassId(registerDTO.getClassId());
    return new Student(
        registerDTO.getName(),
        registerDTO.getEmail(),
        registerDTO.getPassword(),
        true,
        registerDTO.getClassId()
    );
  }

  private void isValidTeacherEmail(String email) {
    Query query = new Query(Criteria.where("email").is(email));
    if(!mongoTemplate.exists(query, "teacher_mails")) {
      throw new HttpClientErrorException(HttpStatusCode.valueOf(403), "This user cannot register as a teacher");
    }
  }

  private void verifyClassId(String classId) {
    if(classroomRepository.findByUuid(classId).isEmpty()) {
      throw new HttpClientErrorException(HttpStatusCode.valueOf(400), "Invalid class id");
    }
  }
}
