package iftm.edu.br.questoes_api.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import iftm.edu.br.questoes_api.models.Questao;


@Repository
public interface QuestaoRepository extends MongoRepository<Questao, String> {
    
    List<Questao> findByDificuldade(int dificuldade);
    List<Questao> findByVisivel(boolean visivel);
    List<Questao> findByTemaId(String temaId);
    
}