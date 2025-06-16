package iftm.edu.br.respostas_api.repositories;

import iftm.edu.br.respostas_api.models.Estatistica;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EstatisticaRepository extends MongoRepository<Estatistica, UUID> {
    Optional<Estatistica> findByAlunoIdAndQuestaoId(UUID alunoId, UUID questaoId);
    List<Estatistica> findByAlunoId(UUID alunoId);
    List<Estatistica> findByQuestaoId(UUID questaoId);
}