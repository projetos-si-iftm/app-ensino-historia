package iftm.edu.br.questao_api.models.DTOConverter;

import iftm.edu.br.Dto.questao.QuestaoDTO;
import iftm.edu.br.Dto.questao.AlternativaDTO;
import iftm.edu.br.questao_api.models.Questao;
import iftm.edu.br.questao_api.models.Alternativa;


import java.util.stream.Collectors;

public class QuestaoDTOConverter {

    // Converte de Questao para QuestaoDTO
    public static QuestaoDTO toDTO(Questao questao) {
        return new QuestaoDTO(
            questao.getId(),
            questao.getTitulo(),
            questao.getEnunciado(),
            questao.getTemaId(),
            questao.getDificuldade(),
            questao.getAlternativas().stream()
                .map(QuestaoDTOConverter::toAlternativaDTO)
                .collect(Collectors.toList()),
            questao.isVisivel(),
            questao.getDataCriacao(),
            questao.getDataAtualizacao(),
            questao.isAtivo()
        );
    }

    // Converte de QuestaoDTO para Questao
    public static Questao toEntity(QuestaoDTO questaoDTO) {
        Questao questao = new Questao();
        questao.setId(questaoDTO.getId());
        questao.setTitulo(questaoDTO.getTitulo());
        questao.setEnunciado(questaoDTO.getEnunciado());
        questao.setTemaId(questaoDTO.getTemaId());
        questao.setDificuldade(questaoDTO.getDificuldade());
        questao.setAlternativas(questaoDTO.getAlternativas().stream()
            .map(QuestaoDTOConverter::toAlternativaEntity)
            .collect(Collectors.toList()));
        questao.setVisivel(questaoDTO.isVisivel());
        questao.setDataCriacao(questaoDTO.getDataCriacao());
        questao.setDataAtualizacao(questaoDTO.getDataAtualizacao());
        questao.setAtivo(questaoDTO.isAtivo());
        return questao;
    }

    // Converte de Alternativa para AlternativaDTO
    private static AlternativaDTO toAlternativaDTO(Alternativa alternativa) {
        return new AlternativaDTO(
            alternativa.getId(),
            alternativa.getTexto(),
            alternativa.isCorreto(),
            alternativa.getDataCriacao(),
            alternativa.getDataAtualizacao(),
            alternativa.isAtivo()
        );
    }

    // Converte de AlternativaDTO para Alternativa
    private static Alternativa toAlternativaEntity(AlternativaDTO alternativaDTO) {
        Alternativa alternativa = new Alternativa();
        alternativa.setId(alternativaDTO.getId());
        alternativa.setTexto(alternativaDTO.getTexto());
        alternativa.setCorreto(alternativaDTO.isCorreto());
        alternativa.setDataCriacao(alternativaDTO.getDataCriacao());
        alternativa.setDataAtualizacao(alternativaDTO.getDataAtualizacao());
        alternativa.setAtivo(alternativaDTO.isAtivo());
        return alternativa;
    }
}
