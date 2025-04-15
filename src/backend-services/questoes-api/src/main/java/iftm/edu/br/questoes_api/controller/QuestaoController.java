package iftm.edu.br.questoes_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import iftm.edu.br.questoes_api.models.Questao;
import iftm.edu.br.questoes_api.service.QuestaoService;

@RestController
@RequestMapping("/api/v1/questoes")
@RequiredArgsConstructor
public class QuestaoController {
    private final QuestaoService questaoService;

    @GetMapping
    public List<Questao> getAllQuestoes() {
        return questaoService.getAllQuestoes();
    }

    @GetMapping("/{id}")
    public Questao getQuestaoById(@PathVariable String id) {
        return questaoService.getQuestaoById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Questao saveQuestao(@RequestBody Questao Questao) {
        return questaoService.saveQuestao(Questao);
    }

    @GetMapping("/tema/{temaId}")
    public List<Questao> getQuestoesByTema(@PathVariable String temaId) {
        return questaoService.getQuestoesByTema(temaId);
    }

    @GetMapping("/dificuldade/{nivel}")
    public List<Questao> getQuestoesByDificuldade(@PathVariable int nivel) {
        return questaoService.getQuestoesByDificuldade(nivel);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestao(@PathVariable String id) {
        questaoService.deleteQuestao(id);
    }
}