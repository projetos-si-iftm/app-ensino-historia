package com.ensinohistoria.models;

import com.iftm.dto.respostasDTO.DTO.EstatisticasDTO;
import com.iftm.dto.respostasDTO.DTO.RespostasDTO;

public class DTOConverter {

    public static RespostasDTO toRespostasDTO(Respostas resposta) {
        return RespostasDTO.builder()
                .id(resposta.getId())
                .alunoId(resposta.getAlunoId())
                .questaoId(resposta.getQuestaoId())
                .alternativaId(resposta.getAlternativaId())
                .dataInicio(resposta.getDataInicio())
                .dataFim(resposta.getDataFim())
                .dataCriacao(resposta.getDataCriacao())
                .dataAtualizacao(resposta.getDataAtualizacao())
                .ativo(resposta.isAtivo())
                .build();
    }

    public static Respostas toRespostas(RespostasDTO dto) {
        Respostas resposta = new Respostas();
        resposta.setId(dto.getId());
        resposta.setAlunoId(dto.getAlunoId());
        resposta.setQuestaoId(dto.getQuestaoId());
        resposta.setAlternativaId(dto.getAlternativaId());
        resposta.setDataInicio(dto.getDataInicio());
        resposta.setDataFim(dto.getDataFim());
        resposta.setDataCriacao(dto.getDataCriacao());
        resposta.setDataAtualizacao(dto.getDataAtualizacao());
        resposta.setAtivo(dto.isAtivo());
        return resposta;
    }
}
