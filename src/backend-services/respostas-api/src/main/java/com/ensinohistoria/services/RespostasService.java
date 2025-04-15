package com.ensinohistoria.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ensinohistoria.exceptions.BadRequestException;
import com.ensinohistoria.exceptions.ResourceNotFoundException;
import com.ensinohistoria.models.Respostas;
import com.ensinohistoria.repositories.RespostasRepository;
import com.iftm.dto.respostasDTO.DTO.EstatisticasDTO;
import com.iftm.dto.respostasDTO.DTO.RespostasDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RespostasService {
    private final RespostasRepository respostasRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    // Registra uma nova resposta
    public RespostasDTO registrarResposta(RespostasDTO respostaDTO) {
        validarCamposObrigatorios(respostaDTO);

        Respostas resposta = Respostas.builder()
                .alunoId(respostaDTO.getAlunoId())
                .questaoId(respostaDTO.getQuestaoId())
                .alternativaId(respostaDTO.getAlternativaId())
                .dataInicio(respostaDTO.getDataInicio())
                .dataFim(respostaDTO.getDataFim())
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .ativo(true)
                .build();

        Respostas savedResposta = respostasRepository.save(resposta);
        return toDTO(savedResposta);
    }

    // Lista todas as respostas
    public List<RespostasDTO> listarTodas() {
        return respostasRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Busca respostas por aluno
    public List<RespostasDTO> listarPorAluno(UUID alunoId) {
        return respostasRepository.findByAlunoId(alunoId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Busca uma resposta por ID
    public RespostasDTO buscarPorId(String id) {
        return respostasRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Resposta não encontrada"));
    }

    // Desativa uma resposta
    public void desativar(String id) {
        Respostas resposta = respostasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resposta não encontrada"));

        resposta.setAtivo(false);
        resposta.setDataAtualizacao(LocalDateTime.now());
        respostasRepository.save(resposta);
    }

    public EstatisticasDTO getEstatisticas(UUID questaoId) {
        List<Respostas> respostas = respostasRepository.findByQuestaoId(questaoId);

        if (respostas.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma resposta encontrada para estaquestão");
        }

        UUID alternativaCorreta = buscarAlternativaCorreta(questaoId);

        long total = respostas.size();
        long corretas = respostas.stream()
                .filter(r -> r.getAlternativaId().equals(alternativaCorreta))
                .count();

        return new EstatisticasDTO(total, corretas, total - corretas);
    }

    private UUID buscarAlternativaCorreta(UUID questaoId) {
        String url = "http://localhost:8081/api/questoes/" + questaoId + "/alternativa-correta";
        return restTemplate.getForObject(url, UUID.class);
    }

    // Validação dos campos obrigatórios
    private void validarCamposObrigatorios(RespostasDTO dto) {
        if (dto.getAlunoId() == null) {
            throw new BadRequestException("ID do aluno é obrigatório");
        }
        if (dto.getQuestaoId() == null) {
            throw new BadRequestException("ID da questão é obrigatória");
        }
        if (dto.getAlternativaId() == null) {
            throw new BadRequestException("ID da alternativa é obrigatória");
        }
    }

    // Conversão para DTO
    private RespostasDTO toDTO(Respostas resposta) {
        return RespostasDTO.builder()
                .id(resposta.getId())
                .alunoId(resposta.getAlunoId())
                .questaoId(resposta.getQuestaoId())
                .alternativaId(resposta.getAlternativaId())
                .dataInicio(resposta.getDataInicio())
                .dataFim(resposta.getDataFim())
                .dataCriacao(resposta.getDataCriacao())
                .dataAtualizacao(resposta.getDataAtualizacao())
                .ativo(resposta.isAtivo())
                .build();
    }
}