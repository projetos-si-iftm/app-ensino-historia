package iftm.edu.br.questoes_api.models;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "temas")
public class Tema {
    @Id
    private String id;
    private String nome;
    private String descricao;
    private boolean visivel;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private boolean ativo;

    private String imagem; //URL da imagem do tema
}