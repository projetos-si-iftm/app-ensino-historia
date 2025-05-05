package iftm.edu.br.respostas_api.controller;

import iftm.edu.br.respostas_api.models.Estatistica;
import iftm.edu.br.respostas_api.service.EstatisticaService;
import iftm.edu.br.Dto.respostas.EstatisticasDTO;
import iftm.edu.br.respostas_api.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/estatisticas")
public class EstatisticaController {

    private final EstatisticaService estatisticaService;

    @Autowired
    public EstatisticaController(EstatisticaService estatisticaService) {
        this.estatisticaService = estatisticaService;
    }

    @GetMapping
    public ResponseEntity<List<Estatistica>> listarTodas() {
        List<Estatistica> estatisticas = estatisticaService.listarTodas();
        return ResponseEntity.ok(estatisticas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estatistica> buscarPorId(@PathVariable UUID id) {
        try {
            Estatistica estatistica = estatisticaService.buscarPorId(id);
            return ResponseEntity.ok(estatistica);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<Estatistica>> listarPorAluno(@PathVariable UUID alunoId) {
        List<Estatistica> estatisticas = estatisticaService.listarPorAluno(alunoId);
        return ResponseEntity.ok(estatisticas);
    }

    @GetMapping("/aluno/{alunoId}/questao/{questaoId}")
    public ResponseEntity<Estatistica> buscarPorAlunoEQuestao(
            @PathVariable UUID alunoId,
            @PathVariable UUID questaoId) {
        try {
            Estatistica estatistica = estatisticaService.buscarPorAlunoEQuestao(alunoId, questaoId);
            return ResponseEntity.ok(estatistica);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/questao/{questaoId}")
    public ResponseEntity<EstatisticasDTO> getEstatisticasPorQuestao(@PathVariable UUID questaoId) {
        try {
            EstatisticasDTO estatisticasDTO = estatisticaService.getEstatisticasPorQuestao(questaoId);
            return ResponseEntity.ok(estatisticasDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Estatistica> salvar(@RequestBody Estatistica estatistica) {
        Estatistica estatisticaSalva = estatisticaService.salvar(estatistica);
        return ResponseEntity.status(HttpStatus.CREATED).body(estatisticaSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estatistica> atualizar(
            @PathVariable UUID id,
            @RequestBody Estatistica estatistica) {
        try {
            // Verifica se existe
            estatisticaService.buscarPorId(id);
            
            // Garante que o ID seja o mesmo
            estatistica.setId(id);
            
            Estatistica estatisticaAtualizada = estatisticaService.salvar(estatistica);
            return ResponseEntity.ok(estatisticaAtualizada);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        try {
            estatisticaService.remover(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Estatistica> atualizarEstatisticas(
            @RequestParam UUID alunoId,
            @RequestParam UUID questaoId,
            @RequestParam boolean acertou) {
        Estatistica estatisticaAtualizada = estatisticaService.atualizarEstatisticas(alunoId, questaoId, acertou);
        return ResponseEntity.ok(estatisticaAtualizada);
    }
}