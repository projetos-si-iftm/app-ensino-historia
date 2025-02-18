package com.iftm.dto.questoesDTO.converter;

import com.iftm.dto.questoesDTO.Dto.AlternativaDTO;
import com.iftm.dto.questoesDTO.Dto.QuestaoDTO;
import com.iftm.dto.questoesDTO.Dto.TemaDTO;
import iftm.edu.br.questoes_api.models.Alternativa;
import iftm.edu.br.questoes_api.models.Questao;
import iftm.edu.br.questoes_api.models.Tema;

import java.util.stream.Collectors;

public class DTOConverter {

    public static AlternativaDTO toAlternativaDTO(Alternativa alternativa) {
        return new AlternativaDTO(
                alternativa.getId(),
                alternativa.getTexto(),
                alternativa.isCorreto(),
                alternativa.getDataCriacao(),
                alternativa.getDataAtualizacao(),
                alternativa.isAtivo()
        );
    }

    public static Alternativa toAlternativa(AlternativaDTO dto) {
        Alternativa alternativa = new Alternativa();
        alternativa.setId(dto.getId());
        alternativa.setTexto(dto.getTexto());
        alternativa.setCorreto(dto.isCorreto());
        alternativa.setDataCriacao(dto.getDataCriacao());
        alternativa.setDataAtualizacao(dto.getDataAtualizacao());
        alternativa.setAtivo(dto.isAtivo());
        return alternativa;
    }

    public static QuestaoDTO toQuestaoDTO(Questao questao) {
        return new QuestaoDTO(
                questao.getId(),
                questao.getTitulo(),
                questao.getEnunciado(),
                questao.getTemaId(),
                questao.getDificuldade(),
                questao.getAlternativas().stream().map(DTOConverter::toAlternativaDTO).collect(Collectors.toList()),
                questao.isVisivel(),
                questao.getDataCriacao(),
                questao.getDataAtualizacao(),
                questao.isAtivo()
        );
    }

    public static Questao toQuestao(QuestaoDTO dto) {
        Questao questao = new Questao();
        questao.setId(dto.getId());
        questao.setTitulo(dto.getTitulo());
        questao.setEnunciado(dto.getEnunciado());
        questao.setTemaId(dto.getTemaId());
        questao.setDificuldade(dto.getDificuldade());
        questao.setAlternativas(dto.getAlternativas().stream().map(DTOConverter::toAlternativa).collect(Collectors.toList()));
        questao.setVisivel(dto.isVisivel());
        questao.setDataCriacao(dto.getDataCriacao());
        questao.setDataAtualizacao(dto.getDataAtualizacao());
        questao.setAtivo(dto.isAtivo());
        return questao;
    }

    public static TemaDTO toTemaDTO(Tema tema) {
        return new TemaDTO(
                tema.getId(),
                tema.getNome(),
                tema.getDescricao(),
                tema.isVisivel(),
                tema.getDataCriacao(),
                tema.getDataAtualizacao(),
                tema.isAtivo()
        );
    }

    public static Tema toTema(TemaDTO dto) {
        Tema tema = new Tema();
        tema.setId(dto.getId());
        tema.setNome(dto.getNome());
        tema.setDescricao(dto.getDescricao());
        tema.setVisivel(dto.isVisivel());
        tema.setDataCriacao(dto.getDataCriacao());
        tema.setDataAtualizacao(dto.getDataAtualizacao());
        tema.setAtivo(dto.isAtivo());
        return tema;
    }
}
