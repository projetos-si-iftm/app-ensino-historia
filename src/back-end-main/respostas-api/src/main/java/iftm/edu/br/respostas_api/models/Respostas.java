package iftm.edu.br.respostas_api.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.nataliaarantes.iftm.model.Student;

import iftm.edu.br.questao_api.models.Alternativa;
import iftm.edu.br.questao_api.models.Questao;

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

    @DBRef
    private Student aluno;

    @DBRef
    private Questao questao;

    @DBRef
    private Alternativa alternativa;

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private boolean acerto;

    @Builder.Default
    private boolean ativo = true;
    

// Adicione estes métodos setter
public void setAlunoId(UUID alunoId) {
    this.aluno = new Student();
    this.aluno.setClassId(alunoId.toString());
}

public void setQuestaoId(UUID questaoId) {
    this.questao = new Questao();
    this.questao.setId(questaoId.toString());
}

public void setAlternativaId(UUID alternativaId) {
    this.alternativa = new Alternativa();
    this.alternativa.setId(alternativaId.toString());
}

public UUID getAlunoId() {
    return UUID.fromString(this.aluno.getClassId());
}

public UUID getQuestaoId() {
    return UUID.fromString(this.questao.getId());
}

public UUID getAlternativaId() {
    return UUID.fromString(this.alternativa.getId());
}

}