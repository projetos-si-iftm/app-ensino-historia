package com.lucashumberto.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "respostas")
public class Respostas {

    @Id
    private UUID id;

    @Field("aluno_id")
    private UUID alunoId; // Referência à coleção "alunos"

    @Field("questao_id")
    private UUID questaoId; // Referência à coleção "questoes"

    @Field("alternativa_id")
    private UUID alternativaId; // Referência à coleção "alternativas"

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    private boolean ativo;
}
