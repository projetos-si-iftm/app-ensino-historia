package com.nataliaarantes.iftm.controller;


import com.nataliaarantes.iftm.model.dto.ValidateTokenDTO;
import com.nataliaarantes.iftm.model.dto.login.LoginDTO;
import com.nataliaarantes.iftm.model.dto.login.LoginResponseDTO;
import com.nataliaarantes.iftm.model.dto.register.RegisterDTO;
import com.nataliaarantes.iftm.model.dto.register.RegisterResponseDTO;
import com.nataliaarantes.iftm.service.AuthorizationService;
import com.nataliaarantes.iftm.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}