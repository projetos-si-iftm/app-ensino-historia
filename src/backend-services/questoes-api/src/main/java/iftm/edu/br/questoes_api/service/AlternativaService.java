package iftm.edu.br.questoes_api.service;

import iftm.edu.br.questoes_api.models.Alternativa;
import iftm.edu.br.questoes_api.models.Dto.AlternativaDTO;
import iftm.edu.br.questoes_api.repositories.AlternativaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlternativaService {

    @Autowired
    private AlternativaRepository alternativaRepository;

    public List<AlternativaDTO> getAllAlternativas() {
        return alternativaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AlternativaDTO getAlternativaById(String id) {
        Optional<Alternativa> alternativa = alternativaRepository.findById(id);
        return alternativa.map(this::toDTO).orElse(null);
    }

    public AlternativaDTO saveAlternativa(AlternativaDTO alternativaDTO) {
        Alternativa alternativa = toEntity(alternativaDTO);
        Alternativa savedAlternativa = alternativaRepository.save(alternativa);
        return toDTO(savedAlternativa);
    }

    public boolean deleteAlternativaById(String id) {
        if (alternativaRepository.existsById(id)) {
            alternativaRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private Alternativa toEntity(AlternativaDTO alternativaDTO) {
        Alternativa alternativa = new Alternativa();
        alternativa.setId(alternativaDTO.getId());
        alternativa.setTexto(alternativaDTO.getTexto());
        alternativa.setCorreto(alternativaDTO.isCorreto());
        alternativa.setAtivo(true);
        
        // Se for uma nova alternativa (sem ID), inicializa as datas
        if (alternativaDTO.getId() == null) {
            alternativa.setDataCriacao(LocalDateTime.now());
            alternativa.setDataAtualizacao(LocalDateTime.now());
        } else {
            // Se for atualização, mantém a data de criação original e atualiza a data de atualização
            Alternativa existingAlternativa = alternativaRepository.findById(alternativaDTO.getId())
                    .orElse(null);
            if (existingAlternativa != null) {
                alternativa.setDataCriacao(existingAlternativa.getDataCriacao());
            }
            alternativa.setDataAtualizacao(LocalDateTime.now());
        }
        
        return alternativa;
    }

    private AlternativaDTO toDTO(Alternativa alternativa) {
        return new AlternativaDTO(
                alternativa.getId(),
                alternativa.getTexto(),
                alternativa.isCorreto(),
                alternativa.getDataCriacao(),
                alternativa.getDataAtualizacao(),
                alternativa.isAtivo()
        );
    }

    public AlternativaDTO updateAlternativa(String id, AlternativaDTO alternativaDTO) {
        if (!alternativaRepository.existsById(id)) {
            return null;
        }
        alternativaDTO.setId(id);
        Alternativa alternativa = toEntity(alternativaDTO);
        Alternativa savedAlternativa = alternativaRepository.save(alternativa);
        return toDTO(savedAlternativa);
    }
}