package iftm.edu.br.questao_api.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import iftm.edu.br.Dto.questao.TemaDTO;
import iftm.edu.br.questao_api.models.Tema;
import iftm.edu.br.questao_api.service.TemaService;

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

    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public TemaDTO salvarTema(@RequestParam("nome") String nome,
            @RequestParam("descricao") String descricao,
            @RequestParam(value = "visivel", defaultValue = "true") boolean visivel,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) throws IOException {

        Tema tema = new Tema();
        tema.setNome(nome);
        tema.setDescricao(descricao);
        tema.setVisivel(visivel);
        tema.setDataCriacao(LocalDateTime.now());
        tema.setDataAtualizacao(LocalDateTime.now());
        tema.setAtivo(true);

        if (imagem != null && !imagem.isEmpty()) {
            tema.setNomeArquivo(imagem.getOriginalFilename());
            tema.setTipoConteudo(imagem.getContentType());
            tema.setDados(imagem.getBytes());
        }

        Tema salvo = temaService.salvarTema(tema);
        return toDTO(salvo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTema(@PathVariable String id) {
        temaService.deleteTema(id);
    }

    @PostMapping("/{id}/imagem")
    public ResponseEntity<TemaDTO> uploadImagem(@PathVariable String id, @RequestParam("file") MultipartFile file)
            throws IOException {
        Tema tema = temaService.getTemaByIdEntity(id); // novo método que retorna Tema
        tema.setNomeArquivo(file.getOriginalFilename());
        tema.setTipoConteudo(file.getContentType());
        tema.setDados(file.getBytes());
        tema.setDataAtualizacao(LocalDateTime.now());

        Tema atualizado = temaService.salvarTema(tema); // novo método para salvar direto o Tema
        return ResponseEntity.ok(toDTO(atualizado));
    }

    
    @GetMapping("/{id}/imagem")
    public ResponseEntity<byte[]> getImagem(@PathVariable String id) {
        Tema tema = temaService.getTemaByIdEntity(id);

        if (tema.getDados() == null || tema.getTipoConteudo() == null) {
            return ResponseEntity.notFound().build(); // Sem imagem
        }

        return ResponseEntity
                .ok()
                .header("Content-Type", tema.getTipoConteudo())
                .header("Content-Disposition", "inline; filename=\"" + tema.getNomeArquivo() + "\"")
                .body(tema.getDados());
    }

    @PutMapping("/{id}/imagem")
    public ResponseEntity<TemaDTO> atualizarImagem(@PathVariable String id, @RequestParam("file") MultipartFile file)
            throws IOException {
        Tema tema = temaService.getTemaByIdEntity(id);

        tema.setNomeArquivo(file.getOriginalFilename());
        tema.setTipoConteudo(file.getContentType());
        tema.setDados(file.getBytes());
        tema.setDataAtualizacao(LocalDateTime.now());

        Tema atualizado = temaService.salvarTema(tema);
        return ResponseEntity.ok(toDTO(atualizado));
    }

    @DeleteMapping("/{id}/imagem")
    public ResponseEntity<Void> deletarImagem(@PathVariable String id) {
        Tema tema = temaService.getTemaByIdEntity(id);

        tema.setNomeArquivo(null);
        tema.setTipoConteudo(null);
        tema.setDados(null);
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
                tema.getNomeArquivo(),
                tema.getTipoConteudo(),
                tema.getDados());
    }
}