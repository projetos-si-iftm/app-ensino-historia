package iftm.edu.br.questao_api.models;

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

    // Campos da imagem
    private String nomeArquivo;
    private String tipoConteudo; // Ex: "image/png"
    private byte[] dados;        // Conteúdo da imagem em binário
}