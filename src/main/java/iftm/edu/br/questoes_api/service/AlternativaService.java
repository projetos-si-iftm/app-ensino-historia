package iftm.edu.br.questoes_api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import iftm.edu.br.questoes_api.models.Alternativa;
import iftm.edu.br.questoes_api.models.Dto.AlternativaDTO;
import iftm.edu.br.questoes_api.repositories.AlternativaRepository;

@Service
@RequiredArgsConstructor
public class AlternativaService {
    private final AlternativaRepository alternativaRepository;

    public List<AlternativaDTO> getAllAlternativas() {
        return alternativaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AlternativaDTO getAlternativaById(String id) {
        Alternativa alternativa = alternativaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alternativa não encontrada."));
        return toDTO(alternativa);
    }

    public AlternativaDTO saveAlternativa(AlternativaDTO alternativaDTO) {
        Alternativa alternativa = toEntity(alternativaDTO);
        alternativa.setDataCriacao(LocalDateTime.now());
        alternativa.setDataAtualizacao(LocalDateTime.now());
        alternativa.setAtivo(true);
        Alternativa savedAlternativa = alternativaRepository.save(alternativa);
        return toDTO(savedAlternativa);
    }

    public AlternativaDTO updateAlternativa(String id, AlternativaDTO alternativaDTO) {
        Alternativa existingAlternativa = alternativaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alternativa não encontrada."));
        
        existingAlternativa.setTexto(alternativaDTO.getTexto());
        existingAlternativa.setCorreto(alternativaDTO.isCorreto());
        existingAlternativa.setDataAtualizacao(LocalDateTime.now());
        
        Alternativa updatedAlternativa = alternativaRepository.save(existingAlternativa);
        return toDTO(updatedAlternativa);
    }

    public void deleteAlternativa(String id) {
        Alternativa alternativa = alternativaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alternativa não encontrada."));
        alternativa.setAtivo(false);
        alternativa.setDataAtualizacao(LocalDateTime.now());
        alternativaRepository.save(alternativa);
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

    private Alternativa toEntity(AlternativaDTO dto) {
        return new Alternativa(
                dto.getId(),
                dto.getTexto(),
                dto.isCorreto(),
                dto.getDataCriacao(),
                dto.getDataAtualizacao(),
                dto.isAtivo()
        );
    }
}