package com.nataliaarantes.iftm.repository;

import iftm.edu.br.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    List<Student> findByClassId(String classId);
    Optional<Student> findByEmail(String email);
}
