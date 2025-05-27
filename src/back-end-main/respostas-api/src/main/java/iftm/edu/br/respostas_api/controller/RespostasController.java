package iftm.edu.br.respostas_api.controller;

import iftm.edu.br.respostas_api.models.dto.RespostasDTO;
import iftm.edu.br.respostas_api.models.Estatistica;
import iftm.edu.br.respostas_api.repositories.EstatisticaRepository;
import iftm.edu.br.respostas_api.service.RespostasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/respostas")
public class RespostasController {

    private final RespostasService respostasService;
    private final EstatisticaRepository estatisticaRepository; // Declare the field

    public RespostasController(RespostasService respostasService, EstatisticaRepository estatisticaRepository) {
        this.respostasService = respostasService;
        this.estatisticaRepository = estatisticaRepository;
    }

    @PostMapping
    public ResponseEntity<RespostasDTO> registrarResposta(@RequestBody RespostasDTO respostaDTO) {
        RespostasDTO respostaRegistrada = respostasService.registrarResposta(respostaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(respostaRegistrada);
    }

    @GetMapping
    public ResponseEntity<List<RespostasDTO>> listarTodasRespostas() {
        List<RespostasDTO> respostas = respostasService.listarTodas();
        return ResponseEntity.ok(respostas);
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<RespostasDTO>> listarPorAluno(@PathVariable UUID alunoId) {
        List<RespostasDTO> respostas = respostasService.listarPorAluno(alunoId);
        return ResponseEntity.ok(respostas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostasDTO> buscarPorId(@PathVariable String id) {
        RespostasDTO resposta = respostasService.buscarPorId(id);
        return ResponseEntity.ok(resposta);
    }

    // DESCOMENTAR QUANDO REALIZAR A INTEGRAÇÃO COM QUESTOES
    // @GetMapping("/estatisticas/{questaoId}")
    // public ResponseEntity<?> getEstatisticas(@PathVariable UUID questaoId) {
    // try {
    // EstatisticasDTO estatisticas = respostasService.getEstatisticas(questaoId);
    // return ResponseEntity.ok(estatisticas);
    // } catch (ResourceNotFoundException ex) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    // }
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativarResposta(@PathVariable String id) {
        respostasService.desativar(id);
        return ResponseEntity.noContent().build();
    }
    
   
    @GetMapping("/estatisticas/{alunoId}")
    public ResponseEntity<List<Estatistica>> getEstatisticas(@PathVariable UUID alunoId) {
        List<Estatistica> estatisticas = estatisticaRepository.findByAlunoId(alunoId);
        return ResponseEntity.ok(estatisticas);
    }
    
}