package iftm.edu.br.respostas_api.models.DTOConverter;

import iftm.edu.br.Dto.respostas.EstatisticasDTO;
import iftm.edu.br.Dto.respostas.RespostasDTO;
import iftm.edu.br.respostas_api.models.Estatistica;
import iftm.edu.br.respostas_api.models.Respostas;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

public class RespostasDTOConverter {

    // Converter de Respostas para RespostasDTO
    public static RespostasDTO toDTO(Respostas respostas) {
        if (respostas == null) {
            return null;
        }

        return RespostasDTO.builder()
                .id(respostas.getId())
                .alunoId(respostas.getAlunoId())
                .questaoId(respostas.getQuestaoId())
                .alternativaId(respostas.getAlternativaId())
                .dataInicio(respostas.getDataInicio())
                .dataFim(respostas.getDataFim())
                .dataCriacao(respostas.getDataCriacao())
                .dataAtualizacao(respostas.getDataAtualizacao())
                .ativo(respostas.isAtivo())
                .build();
    }

    // Converter de RespostasDTO para Respostas
    public static Respostas toEntity(RespostasDTO dto) {
        if (dto == null) {
            return null;
        }

        Respostas respostas = new Respostas();
        respostas.setId(dto.getId());
        respostas.setAlunoId(dto.getAlunoId());
        respostas.setQuestaoId(dto.getQuestaoId());
        respostas.setAlternativaId(dto.getAlternativaId());
        respostas.setDataInicio(dto.getDataInicio());
        respostas.setDataFim(dto.getDataFim());
        respostas.setDataCriacao(dto.getDataCriacao());
        respostas.setDataAtualizacao(dto.getDataAtualizacao());
        respostas.setAtivo(dto.isAtivo());
        
        return respostas;
    }

    // Converter lista de Respostas para lista de RespostasDTO
    public static List<RespostasDTO> toDTOList(List<Respostas> respostasList) {
        if (respostasList == null) {
            return new ArrayList<>();
        }

        List<RespostasDTO> dtoList = new ArrayList<>();
        for (Respostas resposta : respostasList) {
            dtoList.add(toDTO(resposta));
        }
        return dtoList;
    }

    // Converter lista de RespostasDTO para lista de Respostas
    public static List<Respostas> toEntityList(List<RespostasDTO> dtoList) {
        if (dtoList == null) {
            return new ArrayList<>();
        }

        List<Respostas> respostasList = new ArrayList<>();
        for (RespostasDTO dto : dtoList) {
            respostasList.add(toEntity(dto));
        }
        return respostasList;
    }

    // Converter de Estatistica para EstatisticasDTO
    public static EstatisticasDTO toEstatisticasDTO(Estatistica estatistica) {
        if (estatistica == null) {
            return null;
        }

        // Mapeando acertos e erros da Estatistica para os campos do EstatisticasDTO
        Long totalRespostas = (long) (estatistica.getAcertos() + estatistica.getErros());
        Long respostasCorretas = (long) estatistica.getAcertos();
        Long respostasIncorretas = (long) estatistica.getErros();

        return new EstatisticasDTO(
                totalRespostas,
                respostasCorretas,
                respostasIncorretas
        );
    }

    // Converter de EstatisticasDTO para Estatistica
    public static Estatistica toEstatisticasEntity(EstatisticasDTO dto, UUID alunoId, UUID questaoId) {
        if (dto == null) {
            return null;
        }

        Estatistica estatistica = new Estatistica();
        estatistica.setAlunoId(alunoId);
        estatistica.setQuestaoId(questaoId);
        
        // Convertendo os campos do EstatisticasDTO para acertos e erros da Estatistica
        estatistica.setAcertos(dto.getRespostasCorretas().intValue());
        estatistica.setErros(dto.getRespostasIncorretas().intValue());
        
        return estatistica;
    }
}