package com.iftm.dto.respostasDTO.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstatisticasDTO {
    private Long totalRespostas; // Total de respostas para a questão
    private Long respostasCorretas; // Número de respostas corretas
    private Long respostasIncorretas; // Número de respostas incorretas
}