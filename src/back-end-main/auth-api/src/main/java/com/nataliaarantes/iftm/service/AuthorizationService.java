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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Objects;
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
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
            var auth = authenticationManager.authenticate(usernamePassword);
            User userDetails = (User) auth.getPrincipal();
            var token = tokenService.generateToken(userDetails);

            return LoginResponseDTO.builder()
                .token(token)
                .id(userDetails.getUuid())
                .email(userDetails.getEmail())
                .name(userDetails.getName())
                .build();
        } catch (AuthenticationException e) {
            System.err.println("Falha na autenticação para o usuário " + loginDTO.getEmail() + ": " + e.getMessage());
            throw new HttpClientErrorException(HttpStatusCode.valueOf(401), "Credenciais inválidas");
        }
    }

    public LoginResponseDTO loginWithGoogle(RegisterDTO registerDTO) {
        Optional<User> userOptional = userRepository.findByEmail(registerDTO.getEmail());
        User user; // Declare a variável user aqui

        if(userOptional.isPresent()) {
            user = userOptional.get(); // Atribua o usuário existente
        } else {
            boolean isTeacher = false;
            try {
                isValidTeacherEmail(registerDTO.getEmail());
                isTeacher = true;
            } catch (HttpClientErrorException e) { /* Ignorar */ }

            registerDTO.setTeacher(isTeacher);
            registerDTO.setPassword("jss-google-login");
            register(registerDTO); // Tenta registrar o usuário

            user = userRepository.findByEmail(registerDTO.getEmail())
                .orElseThrow(() -> new HttpServerErrorException(HttpStatusCode.valueOf(500), "Erro interno ao buscar usuário recém-registrado"));
        }

        // Agora, construa o LoginResponseDTO usando o builder, preenchendo todos os campos necessários
        String token = tokenService.generateToken(user);
        return LoginResponseDTO.builder()
            .token(token)
            .id(user.getUuid())
            .email(user.getEmail())
            .name(user.getName()) // Garanta que user.getName() retorne o nome
            .build();
    }

    public RegisterResponseDTO register(RegisterDTO registerDTO) {
        Optional<User> user = userRepository.findByEmail(registerDTO.getEmail());
        if (user.isPresent()) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400), "Email já cadastrado");
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
            throw new HttpClientErrorException(HttpStatusCode.valueOf(403), "Este usuário não pode se registrar como professor");
        }
    }

    private void verifyClassId(String classId) {
        if(classId != null && classroomRepository.findByUuid(classId).isEmpty()) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400), "ID da turma inválido");
        }
    }
}