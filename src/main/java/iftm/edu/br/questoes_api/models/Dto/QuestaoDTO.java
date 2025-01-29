package iftm.edu.br.questoes_api.models.Dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestaoDTO {
    private String id;
    private String titulo;
    private String enunciado;
    private String temaId;
    private int dificuldade;
    private List<String> alternativasIds;
    private boolean visivel;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private boolean ativo;
}