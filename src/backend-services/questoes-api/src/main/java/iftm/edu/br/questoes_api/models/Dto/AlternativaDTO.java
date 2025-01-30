package iftm.edu.br.questoes_api.models.Dto;

import java.time.LocalDateTime;

import iftm.edu.br.questoes_api.models.Alternativa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativaDTO {
    private String id;
    private String texto;
    private boolean isCorreto;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private boolean ativo;

     // Construtor que aceita um objeto Alternativa
    public AlternativaDTO(Alternativa alternativa) {
        this.id = alternativa.getId();
        this.texto = alternativa.getTexto();
        this.isCorreto = alternativa.isCorreto();
        this.dataCriacao = alternativa.getDataCriacao();
        this.dataAtualizacao = alternativa.getDataAtualizacao();
        this.ativo = alternativa.isAtivo();
    }
}