package iftm.edu.br.questoes_api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import iftm.edu.br.questoes_api.models.Questao;
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

    public List<Questao> getAllQuestoes() {
        return questaoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Questao getQuestaoById(String id) {
        return questaoRepository.findById(id)
            .map(this::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Questão não encontrada com ID: " + id));
    }

    public Questao saveQuestao(Questao Questao) {
        if (Questao.getTitulo() == null || Questao.getTitulo().trim().isEmpty()) {
            throw new BadRequestException("O título da questão não pode estar vazio");
        }
        if (Questao.getAlternativas() == null || Questao.getAlternativas().isEmpty()) {
            throw new BadRequestException("A questão deve ter pelo menos uma alternativa");
        }

        Questao questao = toEntity(Questao);
        questao.setDataCriacao(LocalDateTime.now());
        questao.setDataAtualizacao(LocalDateTime.now());

        List<Alternativa> alternativas = new ArrayList<>();
        for (Alternativa Alternativa : Questao.getAlternativas()) {
            Alternativa alternativa = new Alternativa();
            alternativa.setTexto(Alternativa.getTexto());
            alternativa.setCorreto(Alternativa.isCorreto());
            alternativa.setDataCriacao(LocalDateTime.now());
            alternativa.setDataAtualizacao(LocalDateTime.now());
            alternativa = alternativaRepository.save(alternativa);
            alternativas.add(alternativa);
        }
        questao.setAlternativas(alternativas);

        Questao savedQuestao = questaoRepository.save(questao);
        return toDTO(savedQuestao);
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

    public List<Questao> getQuestoesByDificuldade(int nivel) {
        List<Questao> questoes = questaoRepository.findByDificuldade(nivel);
        return questoes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private Questao toDTO(Questao questao) {
        List<Alternativa> alternativasDTO = questao.getAlternativas().stream()
                .map(alternativa -> new Alternativa(alternativa.getId(), alternativa.getTexto(), alternativa.isCorreto(), alternativa.getDataCriacao(), alternativa.getDataAtualizacao(), alternativa.isAtivo()))
                .collect(Collectors.toList());

        return new Questao(
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

    private Questao toEntity(Questao dto) {
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

        List<Alternativa> alternativas = new ArrayList<>();
        for (Alternativa Alternativa : dto.getAlternativas()) {
            Alternativa alternativa = new Alternativa();
            alternativa.setTexto(Alternativa.getTexto());
            alternativa.setCorreto(Alternativa.isCorreto());
            alternativa.setDataCriacao(LocalDateTime.now());
            alternativa.setDataAtualizacao(LocalDateTime.now());
            alternativas.add(alternativa);
        }
        questao.setAlternativas(alternativas);

        return questao;
    }

    public List<Questao> getQuestoesByTema(String temaId) {
        List<Questao> questoes = questaoRepository.findByTemaId(temaId);
        return questoes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}