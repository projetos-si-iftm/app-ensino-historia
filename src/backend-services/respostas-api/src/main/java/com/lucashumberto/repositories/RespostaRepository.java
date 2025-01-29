package com.lucashumberto.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.lucashumberto.models.Respostas;
import java.util.UUID;

@Repository
public interface RespostaRepository extends MongoRepository<Respostas, UUID> {

}
