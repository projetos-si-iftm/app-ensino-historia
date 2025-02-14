package com.nataliaarantes.iftm.repository;

import com.nataliaarantes.iftm.model.Student;
import com.nataliaarantes.iftm.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByEmail(String email);
  List<Student> findByClassId(String classId);
}
