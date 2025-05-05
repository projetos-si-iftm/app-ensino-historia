package iftm.edu.br.questao_api.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "alternativas") // Mapeia a coleção do MongoDB
public class Alternativa {
    @Id
    private String id;
    private String texto;
    private UUID questaoId;
    private boolean correto;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private boolean ativo;
   
    public UUID getQuestaoId() {
        return questaoId;
    }

    // Adiciona o método isCorreto
    public boolean isCorreto() {
        return correto;
    }
    
}