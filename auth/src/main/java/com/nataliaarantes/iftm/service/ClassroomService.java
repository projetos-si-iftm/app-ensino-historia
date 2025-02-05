package com.nataliaarantes.iftm.service;

import com.nataliaarantes.iftm.model.Classroom;
import com.nataliaarantes.iftm.model.dto.classroom.ClassroomDTO;
import com.nataliaarantes.iftm.model.dto.classroom.ClassroomResponseDTO;
import com.nataliaarantes.iftm.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomService {

  @Autowired
  private ClassroomRepository classroomRepository;


  public ClassroomResponseDTO create(ClassroomDTO classroomDTO) {
    Optional<Classroom> classroom = classroomRepository.findByName(classroomDTO.getName());
    if(classroom.isPresent()) {
      throw new HttpClientErrorException(HttpStatusCode.valueOf(400), "Classroom already exists");
    }

    Classroom model = Classroom.dtoToMode(classroomDTO);
    model = classroomRepository.save(model);

    return ClassroomResponseDTO.modelToResponseDto(model);
  }

  public ClassroomResponseDTO findByUuid(String uuid) {
    Classroom classroom = classroomRepository.findByUuid(uuid)
        .orElseThrow(() -> new HttpClientErrorException(HttpStatusCode.valueOf(404), "Class not found"));

    return  ClassroomResponseDTO.modelToResponseDto(classroom);
  }

  public List<ClassroomResponseDTO> findAll() {
    return classroomRepository.findAll().stream()
        .map(ClassroomResponseDTO::modelToResponseDto)
        .toList();
  }

  public ClassroomResponseDTO update(String uuid, ClassroomDTO dto) {
    Classroom classroom = classroomRepository.findByUuid(uuid)
        .orElseThrow(() -> new HttpClientErrorException(HttpStatusCode.valueOf(404), "Class not found"));

    if(dto.getName() != null) {
      classroom.setName(dto.getName());
    }

    if(dto.getYear() != null) {
      classroom.setYear(dto.getYear());
    }

    classroom = classroomRepository.save(classroom);
    return ClassroomResponseDTO.modelToResponseDto(classroom);
  }

  public void delete(String uuid) {
    Classroom classroom = classroomRepository.findByUuid(uuid)
        .orElseThrow(() -> new HttpClientErrorException(HttpStatusCode.valueOf(404), "Class not found"));

    classroomRepository.delete(classroom);
  }

}
