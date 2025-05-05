package com.nataliaarantes.iftm.controller;


import iftm.edu.br.Dto.Autenticacao.login.LoginDTO;
import iftm.edu.br.Dto.Autenticacao.login.LoginResponseDTO;
import iftm.edu.br.Dto.Autenticacao.register.RegisterDTO;
import iftm.edu.br.Dto.Autenticacao.register.RegisterResponseDTO;
import iftm.edu.br.Dto.Autenticacao.token.ValidateTokenDTO;
import com.nataliaarantes.iftm.service.AuthorizationService;
import com.nataliaarantes.iftm.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("auth")
public class AuthorizationController {

  @Autowired
  private AuthorizationService authorizationService;

  @Autowired
  private TokenService tokenService;


  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO requestBody){
    var response = authorizationService.login(requestBody);
    return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
  }

  @PostMapping("/register")
  public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterDTO requestBody){
    var response = authorizationService.register(requestBody);
    return new ResponseEntity<>(response, HttpStatusCode.valueOf(201));
  }

  @PostMapping("/validateToken")
  public ResponseEntity<Boolean> validateToken(@RequestBody ValidateTokenDTO validateTokenDTO) {
    var response = tokenService.validateToken(validateTokenDTO.getToken());
    var isValid = !Objects.equals(response, "");

    return new ResponseEntity<>(isValid, HttpStatusCode.valueOf(200));
  }

  @PostMapping("/google")
  public ResponseEntity<LoginResponseDTO> googleLogin(RegisterDTO registerDTO) {
    LoginResponseDTO response = authorizationService.loginWithGoogle(registerDTO);
    return ResponseEntity.ok(response);
  }
}