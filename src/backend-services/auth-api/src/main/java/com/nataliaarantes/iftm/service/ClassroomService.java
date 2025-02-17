package com.nataliaarantes.iftm.service;

import com.nataliaarantes.iftm.model.Classroom;
import com.nataliaarantes.iftm.model.Student;
import com.nataliaarantes.iftm.model.dto.user.Student.StudentResponseDTO;
import com.nataliaarantes.iftm.model.dto.classroom.ClassroomDTO;
import com.nataliaarantes.iftm.model.dto.classroom.ClassroomResponseDTO;
import com.nataliaarantes.iftm.repository.ClassroomRepository;
import com.nataliaarantes.iftm.repository.UserRepository;
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

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TokenService tokenService;


  public ClassroomResponseDTO create(ClassroomDTO classroomDTO, String token) {
    Optional<Classroom> classroom = classroomRepository.findByName(classroomDTO.getName());
    if (classroom.isPresent()) {
      throw new HttpClientErrorException(HttpStatusCode.valueOf(400), "Classroom already exists");
    }

    verifyIsTeacher(token);
    Classroom model = Classroom.dtoToMode(classroomDTO);
    model = classroomRepository.save(model);

    return ClassroomResponseDTO.modelToResponseDto(model);
  }

  public ClassroomResponseDTO findByUuid(String uuid) {
    Classroom classroom = classroomRepository.findByUuid(uuid)
        .orElseThrow(() -> new HttpClientErrorException(HttpStatusCode.valueOf(404), "Class not found"));

    ClassroomResponseDTO response = ClassroomResponseDTO.modelToResponseDto(classroom);
    List<StudentResponseDTO> students = getAllStudentsByClassroom(uuid);
    return response.withStudents(students);
  }


  public List<ClassroomResponseDTO> findAll() {
    return classroomRepository.findAll().stream()
        .map(classroom -> {
          ClassroomResponseDTO dto = ClassroomResponseDTO.modelToResponseDto(classroom);
          List<StudentResponseDTO> allStudentsByClassroom = getAllStudentsByClassroom(dto.getUuid());

          return dto.withStudents(allStudentsByClassroom);
        })
        .toList();
  }

  public ClassroomResponseDTO update(String uuid, ClassroomDTO dto, String token) {

    Classroom classroom = classroomRepository.findByUuid(uuid)
        .orElseThrow(() -> new HttpClientErrorException(HttpStatusCode.valueOf(404), "Class not found"));

    verifyIsTeacher(token);
    if (dto.getName() != null) {
      classroom.setName(dto.getName());
    }

    if (dto.getYear() != null) {
      classroom.setYear(dto.getYear());
    }

    classroom = classroomRepository.save(classroom);
    return ClassroomResponseDTO.modelToResponseDto(classroom);
  }

  public void delete(String uuid, String token) {
    Classroom classroom = classroomRepository.findByUuid(uuid)
        .orElseThrow(() -> new HttpClientErrorException(HttpStatusCode.valueOf(404), "Class not found"));

    verifyIsTeacher(token);
    classroomRepository.delete(classroom);
  }


  private List<StudentResponseDTO> getAllStudentsByClassroom(String uuid) {
    return userRepository.findByClassId(uuid).stream()
        .map(StudentResponseDTO::modelToDto)
        .toList();
  }

  private void verifyIsTeacher(String token) {
    String email = tokenService.validateToken(token.replace("Bearer ", ""));
    if(userRepository.findByEmail(email).get().getClass() == Student.class){
      throw new HttpClientErrorException(HttpStatusCode.valueOf(403), "Student cant manipulate classroom data");
    }
  }

}
