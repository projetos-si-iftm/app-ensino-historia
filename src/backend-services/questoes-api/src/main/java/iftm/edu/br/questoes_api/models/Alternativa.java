package iftm.edu.br.questoes_api.models;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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


    @Field("correto") // Mapeia o campo correto do banco de dados
    private boolean isCorreto;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private boolean ativo;
}