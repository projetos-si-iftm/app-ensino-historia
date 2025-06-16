package iftm.edu.br.questoes_api.service;

import iftm.edu.br.questoes_api.models.Alternativa;
import iftm.edu.br.questoes_api.models.dto.AlternativaDTO;
import iftm.edu.br.questoes_api.repositories.AlternativaRepository;
import iftm.edu.br.questoes_api.exceptions.ResourceNotFoundException;
import iftm.edu.br.questoes_api.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
        return alternativaRepository.findById(id)
            .map(this::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Alternativa não encontrada com ID: " + id));
    }

    public AlternativaDTO saveAlternativa(AlternativaDTO alternativaDTO) {
        if (alternativaDTO.getTexto() == null || alternativaDTO.getTexto().trim().isEmpty()) {
            throw new BadRequestException("O texto da alternativa não pode estar vazio");
        }
        Alternativa alternativa = toEntity(alternativaDTO);
        Alternativa savedAlternativa = alternativaRepository.save(alternativa);
        return toDTO(savedAlternativa);
    }

    public boolean deleteAlternativaById(String id) {
        if (!alternativaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Alternativa não encontrada com ID: " + id);
        }
        alternativaRepository.deleteById(id);
        return true;
    }

    public AlternativaDTO updateAlternativa(String id, AlternativaDTO alternativaDTO) {
        if (!alternativaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Alternativa não encontrada com ID: " + id);
        }
        alternativaDTO.setId(id);
        Alternativa alternativa = toEntity(alternativaDTO);
        Alternativa savedAlternativa = alternativaRepository.save(alternativa);
        return toDTO(savedAlternativa);
    }

    private Alternativa toEntity(AlternativaDTO alternativaDTO) {
        Alternativa alternativa = new Alternativa();
        alternativa.setId(alternativaDTO.getId());
        alternativa.setTexto(alternativaDTO.getTexto());
        alternativa.setCorreto(alternativaDTO.isCorreto());  
        alternativa.setAtivo(true);
        
        if (alternativaDTO.getId() == null) {
            alternativa.setDataCriacao(LocalDateTime.now());
            alternativa.setDataAtualizacao(LocalDateTime.now());
        } else {
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
}