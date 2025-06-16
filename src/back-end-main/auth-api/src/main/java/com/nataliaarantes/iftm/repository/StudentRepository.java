package com.nataliaarantes.iftm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nataliaarantes.iftm.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    List<Student> findByClassId(String classId);
    Optional<Student> findByEmail(String email);
}
