package iftm.edu.br.questoes_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import iftm.edu.br.questoes_api.models.Dto.TemaDTO;
import iftm.edu.br.questoes_api.service.TemaService;

@RestController
@RequestMapping("/temas")
@RequiredArgsConstructor
public class TemaController {
    private final TemaService temaService;

    @GetMapping
    public List<TemaDTO> getAllTemas() {
        return temaService.getAllTemas();
    }

    @GetMapping("/{id}")
    public TemaDTO getTemaById(@PathVariable String id) {
        return temaService.getTemaById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TemaDTO saveTema(@RequestBody TemaDTO temaDTO) {
        return temaService.saveTema(temaDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTema(@PathVariable String id) {
        temaService.deleteTema(id);
    }
}