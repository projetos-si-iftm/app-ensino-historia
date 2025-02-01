package iftm.edu.br.questoes_api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import iftm.edu.br.questoes_api.models.Questao;
import iftm.edu.br.questoes_api.models.Alternativa;
import iftm.edu.br.questoes_api.models.Dto.QuestaoDTO;
import iftm.edu.br.questoes_api.models.Dto.AlternativaDTO;
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
        Questao questao = questaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Questão não encontrada."));
        return toDTO(questao);
    }

    public QuestaoDTO saveQuestao(QuestaoDTO questaoDTO) {
        Questao questao = toEntity(questaoDTO);
        questao.setDataCriacao(LocalDateTime.now());
        questao.setDataAtualizacao(LocalDateTime.now());

        List<Alternativa> alternativas = new ArrayList<>();
        for (AlternativaDTO alternativaDTO : questaoDTO.getAlternativas()) {
            Alternativa alternativa = new Alternativa();
            alternativa.setTexto(alternativaDTO.getTexto());
            alternativa.setCorreto(alternativaDTO.isCorreto());
            alternativa.setDataCriacao(LocalDateTime.now());
            alternativa.setDataAtualizacao(LocalDateTime.now());
            alternativa = alternativaRepository.save(alternativa);
            alternativas.add(alternativa);
        }
        questao.setAlternativas(alternativas);

        Questao savedQuestao = questaoRepository.save(questao);
        return toDTO(savedQuestao);
    }

    public QuestaoDTO updateQuestao(String id, QuestaoDTO questaoDTO) {
        Questao existingQuestao = questaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Questão não encontrada."));

        existingQuestao.setTitulo(questaoDTO.getTitulo());
        existingQuestao.setEnunciado(questaoDTO.getEnunciado());
        existingQuestao.setTemaId(questaoDTO.getTemaId());
        existingQuestao.setDificuldade(questaoDTO.getDificuldade());
        existingQuestao.setVisivel(questaoDTO.isVisivel());
        existingQuestao.setAtivo(questaoDTO.isAtivo());
        existingQuestao.setDataAtualizacao(LocalDateTime.now());

        List<Alternativa> alternativas = new ArrayList<>();
        for (AlternativaDTO alternativaDTO : questaoDTO.getAlternativas()) {
            Alternativa alternativa = new Alternativa();
            alternativa.setTexto(alternativaDTO.getTexto());
            alternativa.setCorreto(alternativaDTO.isCorreto());
            alternativa.setDataCriacao(LocalDateTime.now());
            alternativa.setDataAtualizacao(LocalDateTime.now());
            alternativa = alternativaRepository.save(alternativa);
            alternativas.add(alternativa);
        }
        existingQuestao.setAlternativas(alternativas);

        Questao updatedQuestao = questaoRepository.save(existingQuestao);
        return toDTO(updatedQuestao);
    }

    public void deleteQuestao(String id) {
        if (!questaoRepository.existsById(id)) {
            throw new RuntimeException("Questão não encontrada: " + id);
        }
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
                questao.getTemaId(), // Aqui você pode converter o temaId para texto se necessário
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

        List<Alternativa> alternativas = new ArrayList<>();
        for (AlternativaDTO alternativaDTO : dto.getAlternativas()) {
            Alternativa alternativa = new Alternativa();
            alternativa.setTexto(alternativaDTO.getTexto());
            alternativa.setCorreto(alternativaDTO.isCorreto());
            alternativa.setDataCriacao(LocalDateTime.now());
            alternativa.setDataAtualizacao(LocalDateTime.now());
            alternativas.add(alternativa);
        }
        questao.setAlternativas(alternativas);

        return questao;
    }

    public List<QuestaoDTO> getQuestoesByTema(String temaId) {
        List<Questao> questoes = questaoRepository.findByTemaId(temaId);
        return questoes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}