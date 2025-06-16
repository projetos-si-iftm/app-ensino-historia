package iftm.edu.br.questoes_api.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import iftm.edu.br.questoes_api.models.Tema;
import iftm.edu.br.questoes_api.models.dto.TemaDTO;
import iftm.edu.br.questoes_api.service.TemaService;

@RestController
@CrossOrigin(origins = "*")
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
    public TemaDTO salvarTema(@RequestBody TemaDTO temaDTO) {
        Tema tema = new Tema();
        tema.setNome(temaDTO.getNome());
        tema.setDescricao(temaDTO.getDescricao());
        tema.setVisivel(temaDTO.isVisivel());
        tema.setDataCriacao(LocalDateTime.now());
        tema.setDataAtualizacao(LocalDateTime.now());
        tema.setAtivo(true);
        tema.setImagem(temaDTO.getImagem()); // Salva a URL da imagem

        Tema salvo = temaService.salvarTema(tema);
        return toDTO(salvo);
    }

    @PutMapping("/{id}/imagem")
    public ResponseEntity<TemaDTO> atualizarImagem(@PathVariable String id, @RequestParam("imagem") String imagemUrl) {
        Tema tema = temaService.getTemaByIdEntity(id);

        tema.setImagem(imagemUrl); // Atualiza a URL da imagem
        tema.setDataAtualizacao(LocalDateTime.now());

        Tema atualizado = temaService.salvarTema(tema);
        return ResponseEntity.ok(toDTO(atualizado));
    }

    @DeleteMapping("/{id}/imagem")
    public ResponseEntity<Void> deletarImagem(@PathVariable String id) {
        Tema tema = temaService.getTemaByIdEntity(id);

        tema.setImagem(null); // Remove a URL da imagem
        tema.setDataAtualizacao(LocalDateTime.now());

        temaService.salvarTema(tema);
        return ResponseEntity.noContent().build();
    }

    private TemaDTO toDTO(Tema tema) {
        return new TemaDTO(
                tema.getId(),
                tema.getNome(),
                tema.getDescricao(),
                tema.isVisivel(),
                tema.getDataCriacao(),
                tema.getDataAtualizacao(),
                tema.isAtivo(),
                tema.getImagem()); // Retorna apenas a URL da imagem
    }
}