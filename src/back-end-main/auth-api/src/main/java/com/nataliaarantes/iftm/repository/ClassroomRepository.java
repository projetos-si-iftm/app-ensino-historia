package com.nataliaarantes.iftm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nataliaarantes.iftm.model.Classroom;

import java.util.Optional;

@Repository
public interface ClassroomRepository extends MongoRepository<Classroom, String> {
  Optional<Classroom> findByUuid(String uuid);
  Optional<Classroom> findByName(String name);
}
