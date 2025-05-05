package iftm.edu.br.questao_api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import iftm.edu.br.questao_api.models.Alternativa;

@Repository
public interface AlternativaRepository extends MongoRepository<Alternativa, String> {
}