package iftm.edu.br.questoes_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import iftm.edu.br.questoes_api.models.Dto.AlternativaDTO;
import iftm.edu.br.questoes_api.service.AlternativaService;

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
    public AlternativaDTO getAlternativaById(@PathVariable String id) {
        return alternativaService.getAlternativaById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlternativaDTO saveAlternativa(@RequestBody AlternativaDTO alternativaDTO) {
        return alternativaService.saveAlternativa(alternativaDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlternativa(@PathVariable String id) {
        alternativaService.deleteAlternativa(id);
    }
}