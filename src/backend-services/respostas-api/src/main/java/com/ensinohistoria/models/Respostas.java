package com.ensinohistoria.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "respostas")
@Builder
public class Respostas {

    @Id
    private String id;

    private UUID alunoId; // Coleção: alunos
    private UUID questaoId; // Coleção: questoes
    private UUID alternativaId; // Coleção: alternativas

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    @Builder.Default
    private boolean ativo = true;
}