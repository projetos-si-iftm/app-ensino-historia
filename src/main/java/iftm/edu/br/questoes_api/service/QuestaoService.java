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
        questao.setDataCriacao(dto.getDataCriacao());
        questao.setDataAtualizacao(dto.getDataAtualizacao());
        questao.setAtivo(dto.isAtivo());
        
        return questao;
    }

    public List<QuestaoDTO> getAllQuestoes() {
        List<Questao> questoes = questaoRepository.findAll();
        return questoes.stream()
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
            System.out.println("Iniciando salvamento da questão");
            
            // Verifica se o tema existe
            Tema tema = temaRepository.findById(questaoDTO.getTemaId())
                .orElseThrow(() -> new RuntimeException("Tema não encontrado: " + questaoDTO.getTemaId()));
            System.out.println("Tema encontrado: " + tema.getId());
            
            // Verifica se todas as alternativas existem
            List<Alternativa> alternativas = new ArrayList<>();
            for (String alternativaId : questaoDTO.getAlternativasIds()) {
                Alternativa alternativa = alternativaRepository.findById(alternativaId)
                    .orElseThrow(() -> new RuntimeException("Alternativa não encontrada: " + alternativaId));
                alternativas.add(alternativa);
            }
            System.out.println("Alternativas encontradas: " + alternativas.size());
            
            Questao questao = toEntity(questaoDTO);
            questao.setTemaId(tema.getId());
            questao.setAlternativas(alternativas);
            questao.setDataCriacao(LocalDateTime.now());
            questao.setDataAtualizacao(LocalDateTime.now());
            
            Questao savedQuestao = questaoRepository.save(questao);
            System.out.println("Questão salva com sucesso: " + savedQuestao.getId());
            
            return toDTO(savedQuestao);
        } catch (Exception e) {
            System.err.println("Erro ao salvar questão: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar questão: " + e.getMessage());
        }
    }

    public List<QuestaoDTO> getQuestoesByTema(String temaId) {
        List<Questao> questoes = questaoRepository.findByTemaId(temaId);
        return questoes.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public List<QuestaoDTO> getQuestoesByDificuldade(int nivel) {
        List<Questao> questoes = questaoRepository.findByDificuldade(nivel);
        return questoes.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public void deleteQuestao(String id) {
        Questao questao = questaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Questão não encontrada."));
        questao.setAtivo(false);
        questao.setDataAtualizacao(LocalDateTime.now());
        questaoRepository.save(questao);
    }
}