package iftm.edu.br.respostas_api.repositories;

import iftm.edu.br.respostas_api.models.Respostas;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RespostasRepository extends MongoRepository<Respostas, String> {

    // Busca respostas por aluno
    List<Respostas> findByAlunoId(UUID alunoId);

    // Busca respostas por questão
    List<Respostas> findByQuestaoId(UUID questaoId);

    Optional<Respostas> findById(String id);

    // Adiciona o método findByAlunoIdAndQuestaoId
    Optional<Respostas> findByAlunoIdAndQuestaoId(UUID alunoId, UUID questaoId);
}