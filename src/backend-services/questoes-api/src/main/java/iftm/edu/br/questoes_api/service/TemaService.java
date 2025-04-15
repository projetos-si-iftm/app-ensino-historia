package iftm.edu.br.questoes_api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import iftm.edu.br.questoes_api.exceptions.ResourceNotFoundException;
import iftm.edu.br.questoes_api.exceptions.BadRequestException;
import iftm.edu.br.questoes_api.models.Tema;
import iftm.edu.br.questoes_api.repositories.TemaRepository;

@Service
@RequiredArgsConstructor
public class TemaService {
    private final TemaRepository temaRepository;

    public List<Tema> getAllTemas() {
        return temaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Tema getTemaById(String id) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tema não encontrado com ID: " + id));
        return toDTO(tema);
    }

    public Tema saveTema(Tema Tema) {
        if (Tema.getNome() == null || Tema.getNome().trim().isEmpty()) {
            throw new BadRequestException("O nome do tema não pode estar vazio");
        }
        Tema tema = toEntity(Tema);
        tema.setDataCriacao(LocalDateTime.now());
        tema.setDataAtualizacao(LocalDateTime.now());
        tema.setAtivo(true);
        Tema savedTema = temaRepository.save(tema);
        return toDTO(savedTema);
    }

    public Tema updateTema(String id, Tema Tema) {
        if (!temaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tema não encontrado com ID: " + id);
        }
        Tema existingTema = temaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tema não encontrado com ID: " + id));
        
        existingTema.setNome(Tema.getNome());
        existingTema.setDescricao(Tema.getDescricao());
        existingTema.setVisivel(Tema.isVisivel());
        existingTema.setDataAtualizacao(LocalDateTime.now());
        
        Tema updatedTema = temaRepository.save(existingTema);
        return toDTO(updatedTema);
    }

    public void deleteTema(String id) {
        if (!temaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tema não encontrado com ID: " + id);
        }
        temaRepository.deleteById(id);
    }


    private Tema toDTO(Tema tema) {
        return new Tema(
                tema.getId(),
                tema.getNome(),
                tema.getDescricao(),
                tema.isVisivel(),
                tema.getDataCriacao(),
                tema.getDataAtualizacao(),
                tema.isAtivo()
        );
    }

    private Tema toEntity(Tema dto) {
        return new Tema(
                dto.getId(),
                dto.getNome(),
                dto.getDescricao(),
                dto.isVisivel(),
                dto.getDataCriacao(),
                dto.getDataAtualizacao(),
                dto.isAtivo()
        );
    }
}