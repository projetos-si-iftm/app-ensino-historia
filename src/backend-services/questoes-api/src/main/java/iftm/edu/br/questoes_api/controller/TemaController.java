package iftm.edu.br.questoes_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import iftm.edu.br.questoes_api.models.Tema;
import iftm.edu.br.questoes_api.service.TemaService;

@RestController
@RequestMapping("/temas")
@RequiredArgsConstructor
public class TemaController {
    private final TemaService temaService;

    @GetMapping
    public List<Tema> getAllTemas() {
        return temaService.getAllTemas();
    }

    @GetMapping("/{id}")
    public Tema getTemaById(@PathVariable String id) {
        return temaService.getTemaById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tema saveTema(@RequestBody Tema Tema) {
        return temaService.saveTema(Tema);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTema(@PathVariable String id) {
        temaService.deleteTema(id);
    }
}