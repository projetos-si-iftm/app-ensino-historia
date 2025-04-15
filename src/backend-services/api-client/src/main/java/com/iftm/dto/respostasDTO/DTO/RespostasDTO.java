package com.iftm.dto.respostasDTO.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespostasDTO {
    private String id;

    @NotNull(message = "ID do aluno é obrigatório")
    private UUID alunoId;

    @NotNull(message = "ID da questão é obrigatória")
    private UUID questaoId;

    @NotNull(message = "ID da alternativa é obrigatória")
    private UUID alternativaId;

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private boolean ativo;
}