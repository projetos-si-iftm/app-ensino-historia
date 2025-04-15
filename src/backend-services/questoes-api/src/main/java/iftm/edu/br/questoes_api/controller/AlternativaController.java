package iftm.edu.br.questoes_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import iftm.edu.br.questoes_api.models.Alternativa;
import iftm.edu.br.questoes_api.service.AlternativaService;

@RestController
@RequestMapping("/api/v1/alternativas")
@RequiredArgsConstructor
public class AlternativaController {
    private final AlternativaService alternativaService;

    @GetMapping
    public List<Alternativa> getAllAlternativas() {
        return alternativaService.getAllAlternativas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alternativa> getAlternativaById(@PathVariable String id) {
        Alternativa alternativa = alternativaService.getAlternativaById(id);
        if (alternativa != null) {
            return ResponseEntity.ok(alternativa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Alternativa saveAlternativa(@RequestBody Alternativa alternativa) {
        return alternativaService.saveAlternativa(alternativa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlternativaById(@PathVariable String id) {
        boolean isDeleted = alternativaService.deleteAlternativaById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alternativa> updateAlternativa(@PathVariable String id,
            @RequestBody Alternativa alternativa) {
        Alternativa updatedAlternativa = alternativaService.updateAlternativa(id, alternativa);
        if (updatedAlternativa != null) {
            return ResponseEntity.ok(updatedAlternativa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}