package iftm.edu.br.questoes_api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import iftm.edu.br.questoes_api.models.Alternativa;

@Repository
public interface AlternativaRepository extends MongoRepository<Alternativa, String> {
}