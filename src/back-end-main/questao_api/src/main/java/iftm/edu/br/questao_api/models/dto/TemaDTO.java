package iftm.edu.br.questao_api.models.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemaDTO {
    private String id;
    private String nome;
    private String descricao;
    private boolean visivel;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private boolean ativo;
    
    private String imagem; //URL da imagem do tema
}