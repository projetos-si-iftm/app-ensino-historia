package com.ensinohistoria.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensinohistoria.exceptions.ResourceNotFoundException;
import com.ensinohistoria.services.RespostasService;
import com.iftm.dto.respostasDTO.DTO.EstatisticasDTO;
import com.iftm.dto.respostasDTO.DTO.RespostasDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/respostas")
@RequiredArgsConstructor
public class RespostasController {

    private final RespostasService respostasService;

    @PostMapping
    public ResponseEntity<RespostasDTO> registrarResposta(@RequestBody RespostasDTO respostaDTO) {
        RespostasDTO respostaRegistrada = respostasService.registrarResposta(respostaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(respostaRegistrada);
    }

    @GetMapping
    public ResponseEntity<List<RespostasDTO>> listarTodasRespostas() {
        List<RespostasDTO> respostas = respostasService.listarTodas();
        return ResponseEntity.ok(respostas);
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<RespostasDTO>> listarPorAluno(@PathVariable UUID alunoId) {
        List<RespostasDTO> respostas = respostasService.listarPorAluno(alunoId);
        return ResponseEntity.ok(respostas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostasDTO> buscarPorId(@PathVariable String id) {
        RespostasDTO resposta = respostasService.buscarPorId(id);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/estatisticas/{questaoId}")
    public ResponseEntity<?> getEstatisticas(@PathVariable UUID questaoId) {
        try {
            EstatisticasDTO estatisticas = respostasService.getEstatisticas(questaoId);
            return ResponseEntity.ok(estatisticas);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativarResposta(@PathVariable String id) {
        respostasService.desativar(id);
        return ResponseEntity.noContent().build();
    }
}
