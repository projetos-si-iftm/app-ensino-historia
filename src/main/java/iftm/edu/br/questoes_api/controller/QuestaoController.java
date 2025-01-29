package iftm.edu.br.questoes_api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import iftm.edu.br.questoes_api.models.Dto.QuestaoDTO;
import iftm.edu.br.questoes_api.service.QuestaoService;

@RestController
@RequestMapping("/questoes")
@RequiredArgsConstructor
public class QuestaoController {
    private final QuestaoService questaoService;

    @GetMapping
    public List<QuestaoDTO> getAllQuestoes() {
        return questaoService.getAllQuestoes();
    }

    @GetMapping("/{id}")
    public QuestaoDTO getQuestaoById(@PathVariable String id) {
        return questaoService.getQuestaoById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestaoDTO saveQuestao(@RequestBody QuestaoDTO questaoDTO) {
        return questaoService.saveQuestao(questaoDTO);
    }

    @GetMapping("/tema/{temaId}")
    public List<QuestaoDTO> getQuestoesByTema(@PathVariable String temaId) {
        return questaoService.getQuestoesByTema(temaId);
    }

    @GetMapping("/dificuldade/{nivel}")
    public List<QuestaoDTO> getQuestoesByDificuldade(@PathVariable int nivel) {
        return questaoService.getQuestoesByDificuldade(nivel);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuestao(@PathVariable String id) {
        questaoService.deleteQuestao(id);
    }
}