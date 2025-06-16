package iftm.edu.br.respostas_api.service;

import iftm.edu.br.respostas_api.models.Respostas;
import iftm.edu.br.respostas_api.repositories.RespostasRepository;
import iftm.edu.br.questoes_api.repositories.QuestaoRepository;
import iftm.edu.br.questoes_api.models.Questao;
import iftm.edu.br.respostas_api.models.dto.RespostasDTO;
import iftm.edu.br.questoes_api.models.Alternativa;
import iftm.edu.br.questoes_api.repositories.AlternativaRepository;
import iftm.edu.br.respostas_api.repositories.EstatisticaRepository;
import iftm.edu.br.respostas_api.models.Estatistica;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RespostasService {

    @Autowired
    private RespostasRepository respostaRepository;

    @Autowired
    private QuestaoRepository questaoRepository;

    @Autowired
    private AlternativaRepository alternativaRepository;

    @Autowired
    private EstatisticaRepository estatisticaRepository;

    /**
     * Registra a resposta de um aluno para uma questão específica.
     * Apenas a primeira resposta é considerada para estatísticas.
     *
     * @param alunoId        ID do aluno
     * @param questaoId      ID da questão
     * @param alternativaId  ID da alternativa escolhida
     * @return A resposta registrada
     */
@Transactional
    public RespostasDTO registrarResposta(RespostasDTO respostaDTO) {
        UUID alunoId = respostaDTO.getAlunoId();
        UUID questaoId = respostaDTO.getQuestaoId();
        UUID alternativaId = respostaDTO.getAlternativaId();

        // Verifica se o aluno já respondeu a esta questão
        Optional<Respostas> respostaExistente = respostaRepository.findByAlunoIdAndQuestaoId(alunoId, questaoId);
        if (respostaExistente.isPresent()) {
            // Converte a resposta existente para RespostasDTO e retorna
            return convertToDTO(respostaExistente.get());
        }

        // Converte UUID para String
        String questaoIdString = questaoId.toString();

        // Obtém a questão e verifica se ela existe
        Questao questao = questaoRepository.findById(questaoIdString)
                .orElseThrow(() -> new IllegalArgumentException("Questão não encontrada"));

        // Converte UUID para String
        String alternativaIdString = alternativaId.toString();

        // Obtém a alternativa e verifica se ela existe
        Alternativa alternativa = alternativaRepository.findById(alternativaIdString)
                .orElseThrow(() -> new IllegalArgumentException("Alternativa não encontrada"));

        // Verifica se a alternativa pertence à questão
        if (!alternativa.getQuestaoId().equals(questaoId)) {
            throw new IllegalArgumentException("A alternativa não pertence à questão especificada");
        }

        // Cria uma nova resposta
        Respostas resposta = new Respostas();
        resposta.setId(UUID.randomUUID().toString());
        resposta.setAlunoId(alunoId);
        resposta.setQuestaoId(questaoId);
        resposta.setAlternativaId(alternativaId);
        resposta.setDataCriacao(LocalDateTime.now());
        resposta.setAcerto(alternativa.isCorreto());

        // Salva a resposta no repositório
        respostaRepository.save(resposta);

        // Atualiza as estatísticas do aluno
        atualizarEstatisticas(alunoId, questaoId, alternativa.isCorreto());

        // Converte a resposta para RespostasDTO e retorna
        return convertToDTO(resposta);
    }

    public void desativar(String id) {
        Respostas resposta = respostaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resposta não encontrada"));
        resposta.setAtivo(false);
        resposta.setDataAtualizacao(LocalDateTime.now());
        respostaRepository.save(resposta);
    }

    public List<RespostasDTO> listarTodas() {
        List<Respostas> respostas = respostaRepository.findAll();
        return respostas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RespostasDTO buscarPorId(String id) {
        Respostas resposta = respostaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resposta não encontrada"));
        return convertToDTO(resposta);
    }

    public List<RespostasDTO> listarPorAluno(UUID alunoId) {
        List<Respostas> respostas = respostaRepository.findByAlunoId(alunoId);
        return respostas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    private RespostasDTO convertToDTO(Respostas resposta) {
        return RespostasDTO.builder()
                .id(resposta.getId())
                .alunoId(resposta.getAlunoId())
                .questaoId(resposta.getQuestaoId())
                .alternativaId(resposta.getAlternativaId())
                .dataCriacao(resposta.getDataCriacao())
                .dataAtualizacao(resposta.getDataAtualizacao())
                .ativo(resposta.isAtivo())
                .build();
    }


    /**
     * Atualiza as estatísticas de acertos e erros do aluno para uma questão específica.
     *
     * @param alunoId  ID do aluno
     * @param questaoId  ID da questão
     * @param acertou  Indicador se o aluno acertou a questão
     */
    private void atualizarEstatisticas(UUID alunoId, UUID questaoId, boolean acertou) {
        // Obtém as estatísticas existentes ou cria uma nova entrada
        Estatistica estatistica = estatisticaRepository.findByAlunoIdAndQuestaoId(alunoId, questaoId)
                .orElse(new Estatistica(alunoId, questaoId));

        // Atualiza as estatísticas com base na resposta
        if (acertou) {
            estatistica.incrementarAcertos();
        } else {
            estatistica.incrementarErros();
        }

        // Salva as estatísticas atualizadas no repositório
        estatisticaRepository.save(estatistica);
    }

    
    
}