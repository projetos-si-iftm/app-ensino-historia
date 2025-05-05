package com.nataliaarantes.iftm.controller;

import iftm.edu.br.Dto.Autenticacao.classroom.ClassroomDTO;
import iftm.edu.br.Dto.Autenticacao.classroom.ClassroomResponseDTO;
import com.nataliaarantes.iftm.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("classrooms")
public class ClassroomController {

  @Autowired
  private ClassroomService classroomService;

  @PostMapping
  public ResponseEntity<ClassroomResponseDTO> create(@RequestBody ClassroomDTO requestBody, @RequestHeader("Authorization") String token) {
    var response = classroomService.create(requestBody, token);
    return new ResponseEntity<ClassroomResponseDTO>(response, HttpStatusCode.valueOf(201));
  }

  @GetMapping
  public ResponseEntity<List<ClassroomResponseDTO>> findAll() {
    var response = classroomService.findAll();
    return new ResponseEntity<List<ClassroomResponseDTO>>(response, HttpStatusCode.valueOf(200));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClassroomResponseDTO> findByUuid(@PathVariable String id) {
    var response = classroomService.findByUuid(id);
    return new ResponseEntity<ClassroomResponseDTO>(response, HttpStatusCode.valueOf(200));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ClassroomResponseDTO> update(
      @PathVariable String id,
      @RequestBody ClassroomDTO dto,
      @RequestHeader("Authorization") String token
  ) {
    var response = classroomService.update(id, dto, token);
    return new ResponseEntity<ClassroomResponseDTO>(response, HttpStatusCode.valueOf(200));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id, @RequestHeader("Authorization") String token) {
    classroomService.delete(id, token);
    return new ResponseEntity<Void>(HttpStatusCode.valueOf(204));
  }
}
