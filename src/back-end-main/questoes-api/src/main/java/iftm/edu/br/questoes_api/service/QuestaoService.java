package iftm.edu.br.questoes_api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import iftm.edu.br.questoes_api.models.Questao;
import iftm.edu.br.questoes_api.models.dto.AlternativaDTO;
import iftm.edu.br.questoes_api.models.dto.QuestaoDTO;
import iftm.edu.br.questoes_api.exceptions.ResourceNotFoundException;
import iftm.edu.br.questoes_api.exceptions.BadRequestException;
import iftm.edu.br.questoes_api.models.Alternativa;
import iftm.edu.br.questoes_api.repositories.QuestaoRepository;
import iftm.edu.br.questoes_api.repositories.AlternativaRepository;

@Service
@RequiredArgsConstructor
public class QuestaoService {
    private final QuestaoRepository questaoRepository;
    private final AlternativaRepository alternativaRepository;

    public List<QuestaoDTO> getAllQuestoes() {
        return questaoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public QuestaoDTO getQuestaoById(String id) {
        return questaoRepository.findById(id)
            .map(this::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Questão não encontrada com ID: " + id));
    }
    public QuestaoDTO saveQuestao(QuestaoDTO questaoDTO) {
        if (questaoDTO.getTitulo() == null || questaoDTO.getTitulo().trim().isEmpty()) {
            throw new BadRequestException("O título da questão não pode estar vazio");
        }
        if (questaoDTO.getAlternativas() == null || questaoDTO.getAlternativas().isEmpty()) {
            throw new BadRequestException("A questão deve ter pelo menos uma alternativa");
        }
    
        Questao questao = toEntity(questaoDTO);
        questao.setId(UUID.randomUUID().toString()); // Gera um UUID válido
        questao.setDataCriacao(LocalDateTime.now());
        questao.setDataAtualizacao(LocalDateTime.now());
    
        Questao savedQuestao = questaoRepository.save(questao);
    
        List<Alternativa> alternativas = new ArrayList<>();
        for (AlternativaDTO alternativaDTO : questaoDTO.getAlternativas()) {
            Alternativa alternativa = new Alternativa();
            alternativa.setTexto(alternativaDTO.getTexto());
            alternativa.setCorreto(alternativaDTO.isCorreto());
            alternativa.setDataCriacao(LocalDateTime.now());
            alternativa.setDataAtualizacao(LocalDateTime.now());
            alternativa.setAtivo(true);
            alternativa.setQuestaoId(UUID.fromString(savedQuestao.getId())); // Agora é um UUID válido
    
            alternativa = alternativaRepository.save(alternativa);
            alternativas.add(alternativa);
        }
    
        savedQuestao.setAlternativas(alternativas);
        questaoRepository.save(savedQuestao);
    
        return toDTO(savedQuestao);
    }

    public QuestaoDTO updateQuestao(String id, QuestaoDTO questaoDTO) {
        // Verificar se a questão existe
        Questao existingQuestao = questaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Questão não encontrada com ID: " + id));
        
        // Atualizar campos básicos
        existingQuestao.setTitulo(questaoDTO.getTitulo());
        existingQuestao.setEnunciado(questaoDTO.getEnunciado());
        existingQuestao.setTemaId(questaoDTO.getTemaId());
        existingQuestao.setDificuldade(questaoDTO.getDificuldade());
        existingQuestao.setVisivel(questaoDTO.isVisivel());
        existingQuestao.setDataAtualizacao(LocalDateTime.now());
        
        // Atualizar alternativas
        if (questaoDTO.getAlternativas() != null && !questaoDTO.getAlternativas().isEmpty()) {
            // Excluir alternativas antigas
            if (existingQuestao.getAlternativas() != null && !existingQuestao.getAlternativas().isEmpty()) {
                alternativaRepository.deleteAll(existingQuestao.getAlternativas());
            }
            
            // Criar novas alternativas
            List<Alternativa> alternativas = new ArrayList<>();
            for (AlternativaDTO alternativaDTO : questaoDTO.getAlternativas()) {
                Alternativa alternativa = new Alternativa();
                alternativa.setTexto(alternativaDTO.getTexto());
                alternativa.setCorreto(alternativaDTO.isCorreto());
                alternativa.setDataCriacao(LocalDateTime.now());
                alternativa.setDataAtualizacao(LocalDateTime.now());
                alternativa.setAtivo(true);
                alternativa = alternativaRepository.save(alternativa);
                alternativas.add(alternativa);
            }
            existingQuestao.setAlternativas(alternativas);
        }
        
        Questao updatedQuestao = questaoRepository.save(existingQuestao);
        return toDTO(updatedQuestao);
    }

    public QuestaoDTO updateVisibilidade(String id, boolean visivel) {
        // Buscar a questão
        Questao questao = questaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Questão não encontrada com ID: " + id));
        
        // Atualizar visibilidade
        questao.setVisivel(visivel);
        questao.setDataAtualizacao(LocalDateTime.now());
        
        Questao updatedQuestao = questaoRepository.save(questao);
        return toDTO(updatedQuestao);
    }

    public void deleteQuestao(String id) {
        // Busca a questão
        Questao questao = questaoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Questão não encontrada com ID: " + id));
        
        // Deleta as alternativas associadas
        if (questao.getAlternativas() != null && !questao.getAlternativas().isEmpty()) {
            alternativaRepository.deleteAll(questao.getAlternativas());
        }
        
        // Deleta a questão
        questaoRepository.deleteById(id);
    }

    public List<QuestaoDTO> getQuestoesByDificuldade(int nivel) {
        List<Questao> questoes = questaoRepository.findByDificuldade(nivel);
        return questoes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private QuestaoDTO toDTO(Questao questao) {
        List<AlternativaDTO> alternativasDTO = questao.getAlternativas().stream()
                .map(alternativa -> new AlternativaDTO(alternativa.getId(), alternativa.getTexto(), alternativa.isCorreto(), alternativa.getDataCriacao(), alternativa.getDataAtualizacao(), alternativa.isAtivo()))
                .collect(Collectors.toList());

        return new QuestaoDTO(
                questao.getId(),
                questao.getTitulo(),
                questao.getEnunciado(),
                questao.getTemaId(),
                questao.getDificuldade(),
                alternativasDTO,
                questao.isVisivel(),
                questao.getDataCriacao(),
                questao.getDataAtualizacao(),
                questao.isAtivo()
        );
    }

    private Questao toEntity(QuestaoDTO dto) {
        Questao questao = new Questao();
        questao.setId(dto.getId());
        questao.setTitulo(dto.getTitulo());
        questao.setEnunciado(dto.getEnunciado());
        questao.setTemaId(dto.getTemaId());
        questao.setDificuldade(dto.getDificuldade());
        questao.setVisivel(dto.isVisivel());
        questao.setDataCriacao(dto.getDataCriacao());
        questao.setDataAtualizacao(dto.getDataAtualizacao());
        questao.setAtivo(dto.isAtivo());

        return questao;
    }

    public List<QuestaoDTO> getQuestoesByTema(String temaId) {
        List<Questao> questoes = questaoRepository.findByTemaId(temaId);
        return questoes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
