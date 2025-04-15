package iftm.edu.br.questoes_api.service;

import iftm.edu.br.questoes_api.models.Alternativa;
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

    public List<Alternativa> getAllAlternativas() {
        return alternativaRepository.findAll().stream()
                // .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Alternativa getAlternativaById(String id) {
        return alternativaRepository.findById(id)
                // .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Alternativa não encontrada com ID: " + id));
    }

    public Alternativa saveAlternativa(Alternativa Alternativa) {
        if (Alternativa.getTexto() == null || Alternativa.getTexto().trim().isEmpty()) {
            throw new BadRequestException("O texto da alternativa não pode estar vazio");
        }
        Alternativa alternativa = toEntity(Alternativa);
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

    public Alternativa updateAlternativa(String id, Alternativa Alternativa) {
        if (!alternativaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Alternativa não encontrada com ID: " + id);
        }
        Alternativa.setId(id);
        Alternativa alternativa = toEntity(Alternativa);
        Alternativa savedAlternativa = alternativaRepository.save(alternativa);
        // return toDTO(savedAlternativa);
        return savedAlternativa;
    }

    private Alternativa toEntity(Alternativa Alternativa) {
        Alternativa alternativa = new Alternativa();
        alternativa.setId(Alternativa.getId());
        alternativa.setTexto(Alternativa.getTexto());
        alternativa.setCorreto(Alternativa.isCorreto());
        alternativa.setAtivo(true);

        if (Alternativa.getId() == null) {
            alternativa.setDataCriacao(LocalDateTime.now());
            alternativa.setDataAtualizacao(LocalDateTime.now());
        } else {
            Alternativa existingAlternativa = alternativaRepository.findById(Alternativa.getId())
                    .orElse(null);
            if (existingAlternativa != null) {
                alternativa.setDataCriacao(existingAlternativa.getDataCriacao());
            }
            alternativa.setDataAtualizacao(LocalDateTime.now());
        }

        return alternativa;
    }

    private Alternativa toDTO(Alternativa alternativa) {
        return new Alternativa(
                alternativa.getId(),
                alternativa.getTexto(),
                alternativa.isCorreto(),
                alternativa.getDataCriacao(),
                alternativa.getDataAtualizacao(),
                alternativa.isAtivo());
    }
}