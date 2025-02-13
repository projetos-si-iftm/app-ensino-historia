package com.nataliaarantes.iftm.controller;

import com.nataliaarantes.iftm.model.dto.user.UserDTO;
import com.nataliaarantes.iftm.model.dto.user.UserResponseDTO;
import com.nataliaarantes.iftm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  UserService userService;


  @PutMapping
  public ResponseEntity<UserResponseDTO> update(@RequestBody UserDTO requestBody,
                                                @RequestHeader("Authorization") String token) {
    var response = userService.update(requestBody, token);
    return new ResponseEntity<UserResponseDTO>(response, HttpStatusCode.valueOf(200));
  }
}
