package com.nataliaarantes.iftm.controller;


import com.nataliaarantes.iftm.model.dto.login.LoginDTO;
import com.nataliaarantes.iftm.model.dto.login.LoginResponseDTO;
import com.nataliaarantes.iftm.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthorizationController {

  @Autowired
  private AuthorizationService authorizationService;

//  @Autowired
//  private TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO requestBody){
    var response = authorizationService.login(requestBody);
    return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
  }

  @PostMapping("/register")
  public ResponseEntity register(){
//    if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
//
//    String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
//    User newUser = new User(data.login(), encryptedPassword, data.role());
//
//    this.repository.save(newUser);
//
//    return ResponseEntity.ok().build();
    return null;
  }
}