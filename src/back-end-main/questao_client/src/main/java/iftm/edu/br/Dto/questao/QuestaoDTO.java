package iftm.edu.br.Dto.questao;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestaoDTO {
    private String id;
    @NotNull
    @Size(min = 3, max = 150)
    private String titulo;

    @NotBlank(message = "O enunciado não pode estar vazio")
    private String enunciado;
    private String temaId;
   
    @Min(1) @Max(3)
    private int dificuldade;
    private List<AlternativaDTO> alternativas;
    private boolean visivel;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private boolean ativo;
}