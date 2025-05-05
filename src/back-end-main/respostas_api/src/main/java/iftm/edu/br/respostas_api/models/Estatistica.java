package iftm.edu.br.respostas_api.models;


import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "estatisticas")
public class Estatistica {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "aluno_id", nullable = false)
    private UUID alunoId;

    @Column(name = "questao_id", nullable = false)
    private UUID questaoId;

    @Column(name = "acertos", nullable = false)
    private int acertos;

    @Column(name = "erros", nullable = false)
    private int erros;

    // Construtor padrão
    public Estatistica() {
    }

    // Construtor com parâmetros
    public Estatistica(UUID alunoId, UUID questaoId) {
        this.alunoId = alunoId;
        this.questaoId = questaoId;
        this.acertos = 0;
        this.erros = 0;
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(UUID alunoId) {
        this.alunoId = alunoId;
    }

    public UUID getQuestaoId() {
        return questaoId;
    }

    public void setQuestaoId(UUID questaoId) {
        this.questaoId = questaoId;
    }

    public int getAcertos() {
        return acertos;
    }

    public void setAcertos(int acertos) {
        this.acertos = acertos;
    }

    public int getErros() {
        return erros;
    }

    public void setErros(int erros) {
        this.erros = erros;
    }

    // Métodos para incrementar acertos e erros
    public void incrementarAcertos() {
        this.acertos++;
    }

    public void incrementarErros() {
        this.erros++;
    }
}