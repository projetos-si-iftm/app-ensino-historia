package iftm.edu.br.respostas_api.service;

import iftm.edu.br.respostas_api.models.Estatistica;
import iftm.edu.br.respostas_api.repositories.EstatisticaRepository;
import iftm.edu.br.respostas_api.models.dto.EstatisticasDTO;
import iftm.edu.br.respostas_api.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class EstatisticaService {

    @Autowired
    private EstatisticaRepository estatisticaRepository;

    /**
     * Busca todas as estatísticas armazenadas no sistema
     *
     * @return Lista de todas as estatísticas
     */
    public List<Estatistica> listarTodas() {
        return estatisticaRepository.findAll();
    }

    /**
     * Busca estatísticas por ID
     *
     * @param id ID da estatística
     * @return A estatística encontrada
     * @throws ResourceNotFoundException se não encontrar a estatística
     */
    public Estatistica buscarPorId(UUID id) {
        return estatisticaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estatística não encontrada com o ID: " + id));
    }

    /**
     * Busca estatísticas por ID do aluno
     *
     * @param alunoId ID do aluno
     * @return Lista de estatísticas do aluno
     */
    public List<Estatistica> listarPorAluno(UUID alunoId) {
        return estatisticaRepository.findByAlunoId(alunoId);
    }

    /**
     * Busca estatística específica de um aluno para uma questão
     *
     * @param alunoId ID do aluno
     * @param questaoId ID da questão
     * @return A estatística encontrada
     * @throws ResourceNotFoundException se não encontrar a estatística
     */
    public Estatistica buscarPorAlunoEQuestao(UUID alunoId, UUID questaoId) {
        return estatisticaRepository.findByAlunoIdAndQuestaoId(alunoId, questaoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Estatística não encontrada para o aluno ID: " + alunoId + " e questão ID: " + questaoId));
    }

    /**
     * Obtém estatísticas gerais para uma questão específica
     *
     * @param questaoId ID da questão
     * @return DTO com as estatísticas da questão
     */
    public EstatisticasDTO getEstatisticasPorQuestao(UUID questaoId) {
        List<Estatistica> estatisticas = estatisticaRepository.findByQuestaoId(questaoId);
        
        if (estatisticas.isEmpty()) {
            throw new ResourceNotFoundException("Não há estatísticas para a questão ID: " + questaoId);
        }
        
        long totalRespostas = estatisticas.stream()
                .mapToInt(e -> e.getAcertos() + e.getErros())
                .sum();
                
        long respostasCorretas = estatisticas.stream()
                .mapToInt(Estatistica::getAcertos)
                .sum();
                
        long respostasIncorretas = estatisticas.stream()
                .mapToInt(Estatistica::getErros)
                .sum();
                
        return new EstatisticasDTO(totalRespostas, respostasCorretas, respostasIncorretas);
    }

    /**
     * Salva ou atualiza estatísticas
     *
     * @param estatistica A estatística a ser salva
     * @return A estatística salva
     */
    @Transactional
    public Estatistica salvar(Estatistica estatistica) {
        return estatisticaRepository.save(estatistica);
    }

    /**
     * Remove estatísticas
     *
     * @param id ID da estatística a ser removida
     */
    @Transactional
    public void remover(UUID id) {
        if (!estatisticaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Estatística não encontrada com o ID: " + id);
        }
        estatisticaRepository.deleteById(id);
    }

    /**
     * Atualiza estatísticas para um aluno e questão específicos
     *
     * @param alunoId ID do aluno
     * @param questaoId ID da questão
     * @param acertou Indica se o aluno acertou a questão
     * @return A estatística atualizada
     */
    @Transactional
    public Estatistica atualizarEstatisticas(UUID alunoId, UUID questaoId, boolean acertou) {
        Estatistica estatistica = estatisticaRepository.findByAlunoIdAndQuestaoId(alunoId, questaoId)
                .orElse(new Estatistica(alunoId, questaoId));

        if (acertou) {
            estatistica.incrementarAcertos();
        } else {
            estatistica.incrementarErros();
        }

        return estatisticaRepository.save(estatistica);
    }
}
