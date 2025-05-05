package iftm.edu.br.questao_api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import iftm.edu.br.questao_api.exceptions.ResourceNotFoundException;
import iftm.edu.br.questao_api.exceptions.BadRequestException;
import iftm.edu.br.questao_api.models.Tema;
import iftm.edu.br.Dto.questao.TemaDTO;
import iftm.edu.br.questao_api.repositories.TemaRepository;

@Service
@RequiredArgsConstructor
public class TemaService {
    private final TemaRepository temaRepository;

    public List<TemaDTO> getAllTemas() {
        return temaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public TemaDTO getTemaById(String id) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tema não encontrado com ID: " + id));
        return toDTO(tema);
    }

    public TemaDTO saveTema(TemaDTO temaDTO) {
        if (temaDTO.getNome() == null || temaDTO.getNome().trim().isEmpty()) {
            throw new BadRequestException("O nome do tema não pode estar vazio");
        }
        Tema tema = toEntity(temaDTO);
        tema.setDataCriacao(LocalDateTime.now());
        tema.setDataAtualizacao(LocalDateTime.now());
        tema.setAtivo(true);
        Tema savedTema = temaRepository.save(tema);
        return toDTO(savedTema);
    }

    public TemaDTO updateTema(String id, TemaDTO temaDTO) {
        if (!temaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tema não encontrado com ID: " + id);
        }
        Tema existingTema = temaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tema não encontrado com ID: " + id));
        
        existingTema.setNome(temaDTO.getNome());
        existingTema.setDescricao(temaDTO.getDescricao());
        existingTema.setVisivel(temaDTO.isVisivel());
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


    private TemaDTO toDTO(Tema tema) {
        return new TemaDTO(
            tema.getId(),
            tema.getNome(),
            tema.getDescricao(),
            tema.isVisivel(),
            tema.getDataCriacao(),
            tema.getDataAtualizacao(),
            tema.isAtivo(),
            tema.getNomeArquivo(),
            tema.getTipoConteudo(),
            tema.getDados()
        );
    }
    
    private Tema toEntity(TemaDTO dto) {
        return new Tema(
            dto.getId(),
            dto.getNome(),
            dto.getDescricao(),
            dto.isVisivel(),
            dto.getDataCriacao(),
            dto.getDataAtualizacao(),
            dto.isAtivo(),
            dto.getNomeArquivo(),
            dto.getTipoConteudo(),
            dto.getDados()
        );
    }

    public Tema getTemaByIdEntity(String id) {
        return temaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tema não encontrado com ID: " + id));
    }
    
    public Tema salvarTema(Tema tema) {
        return temaRepository.save(tema);
    }
    
}