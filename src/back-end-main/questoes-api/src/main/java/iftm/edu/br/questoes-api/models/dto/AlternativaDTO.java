package iftm.edu.br.questao_api.models.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativaDTO {
    private String id;
    private String texto;

    @JsonProperty("isCorreto")
    private boolean correto;
     private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private boolean ativo;

    // Construtor que aceita String, String e boolean
    public AlternativaDTO(String id, String texto, boolean correto) {
        this.id = id;
        this.texto = texto;
        this.correto = correto;
    }
}