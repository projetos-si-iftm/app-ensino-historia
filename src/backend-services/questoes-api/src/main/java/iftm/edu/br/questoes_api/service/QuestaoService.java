package iftm.edu.br.questoes_api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import iftm.edu.br.questoes_api.models.Alternativa;
import iftm.edu.br.questoes_api.models.Questao;
import iftm.edu.br.questoes_api.models.Tema;
import iftm.edu.br.questoes_api.models.Dto.QuestaoDTO;
import iftm.edu.br.questoes_api.repositories.AlternativaRepository;
import iftm.edu.br.questoes_api.repositories.QuestaoRepository;
import iftm.edu.br.questoes_api.repositories.TemaRepository;

@Service
@RequiredArgsConstructor
public class QuestaoService {
    private final QuestaoRepository questaoRepository;
    private final TemaRepository temaRepository;
    private final AlternativaRepository alternativaRepository;

    private QuestaoDTO toDTO(Questao questao) {
        return new QuestaoDTO(
            questao.getId(),
            questao.getTitulo(),
            questao.getEnunciado(),
            questao.getTemaId(),
            questao.getDificuldade(),
            questao.getAlternativas() != null ? 
                questao.getAlternativas().stream()
                    .map(Alternativa::getId)
                    .collect(Collectors.toList()) : 
                new ArrayList<>(),
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
        
        if (dto.getTemaId() != null) {
            Tema tema = temaRepository.findById(dto.getTemaId())
                .orElseThrow(() -> new RuntimeException("Tema não encontrado"));
            questao.setTemaId(tema.getId());
        }
        
        questao.setDificuldade(dto.getDificuldade());
        
        if (dto.getAlternativasIds() != null) {
            questao.setAlternativas(dto.getAlternativasIds().stream()
                .map(id -> alternativaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Alternativa não encontrada: " + id)))
                .collect(Collectors.toList()));
        }
        
        questao.setVisivel(dto.isVisivel());
        questao.setAtivo(true);
        return questao;
    }

    public List<QuestaoDTO> getAllQuestoes() {
        return questaoRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public QuestaoDTO getQuestaoById(String id) {
        Questao questao = questaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Questão não encontrada: " + id));
        return toDTO(questao);
    }

    public QuestaoDTO saveQuestao(QuestaoDTO questaoDTO) {
        try {
            Tema tema = temaRepository.findById(questaoDTO.getTemaId())
                .orElseThrow(() -> new RuntimeException("Tema não encontrado: " + questaoDTO.getTemaId()));
            
            List<Alternativa> alternativas = new ArrayList<>();
            for (String alternativaId : questaoDTO.getAlternativasIds()) {
                Alternativa alternativa = alternativaRepository.findById(alternativaId)
                    .orElseThrow(() -> new RuntimeException("Alternativa não encontrada: " + alternativaId));
                alternativas.add(alternativa);
            }
            
            Questao questao = toEntity(questaoDTO);
            questao.setTemaId(tema.getId());
            questao.setAlternativas(alternativas);
            
            if (questao.getId() == null) {
                questao.setDataCriacao(LocalDateTime.now());
            }
            questao.setDataAtualizacao(LocalDateTime.now());
            
            return toDTO(questaoRepository.save(questao));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar questão: " + e.getMessage());
        }
    }

    public List<QuestaoDTO> getQuestoesByTema(String temaId) {
        return questaoRepository.findByTemaId(temaId).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public List<QuestaoDTO> getQuestoesByDificuldade(int nivel) {
        return questaoRepository.findByDificuldade(nivel).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public void deleteQuestao(String id) {
        if (!questaoRepository.existsById(id)) {
            throw new RuntimeException("Questão não encontrada: " + id);
        }
        questaoRepository.deleteById(id);
    }
}