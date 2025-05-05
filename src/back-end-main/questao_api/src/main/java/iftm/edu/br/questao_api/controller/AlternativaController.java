package iftm.edu.br.questao_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import iftm.edu.br.Dto.questao.AlternativaDTO;
import iftm.edu.br.questao_api.service.AlternativaService;

@RestController
@RequestMapping("/alternativas")
@RequiredArgsConstructor
public class AlternativaController {
    private final AlternativaService alternativaService;

    @GetMapping
    public List<AlternativaDTO> getAllAlternativas() {
        return alternativaService.getAllAlternativas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlternativaDTO> getAlternativaById(@PathVariable String id) {
        AlternativaDTO alternativaDTO = alternativaService.getAlternativaById(id);
        if (alternativaDTO != null) {
            return ResponseEntity.ok(alternativaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlternativaDTO saveAlternativa(@RequestBody AlternativaDTO alternativaDTO) {
        return alternativaService.saveAlternativa(alternativaDTO);
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
public ResponseEntity<AlternativaDTO> updateAlternativa(@PathVariable String id, 
                                                       @RequestBody AlternativaDTO alternativaDTO) {
    AlternativaDTO updatedAlternativa = alternativaService.updateAlternativa(id, alternativaDTO);
    if (updatedAlternativa != null) {
        return ResponseEntity.ok(updatedAlternativa);
    } else {
        return ResponseEntity.notFound().build();
    }
}
}