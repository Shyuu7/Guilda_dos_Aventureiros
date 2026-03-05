package br.com.infnet.dr1tp1.controller;

import br.com.infnet.dr1tp1.domain.Aventureiro;
import br.com.infnet.dr1tp1.dto.*;
import br.com.infnet.dr1tp1.mapper.AventureiroMapper;
import br.com.infnet.dr1tp1.service.AventureiroService;
import br.com.infnet.dr1tp1.enums.Classes;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/aventureiros")
public class AventureiroController {
    private final AventureiroService aventureiroService;
    private final AventureiroMapper aventureiroMapper;

    @PostMapping
    public ResponseEntity<AventureiroResponse> criarAventureiro(
            @Valid @RequestBody AventureiroCriacaoRequest request) {
        Aventureiro aventureiro = aventureiroMapper.toEntity(request);
        Aventureiro salvoNoRepo = aventureiroService.criarAventureiro(aventureiro);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(aventureiroMapper.toResponse(salvoNoRepo));
    }

    @GetMapping
    public ResponseEntity<List<AventureiroResumoResponse>> listarAventureiros(
            @RequestParam(required = false) Classes classe,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Integer nivelMinimo,
            @RequestHeader(value = "X-Page", defaultValue = "0")
            @Min(value = 0, message = "O número da página deve ser maior ou igual a 0")
            int page,
            @RequestHeader(value = "X-Size", defaultValue = "10")
            @Range(min = 1, max = 50, message = "Apenas é permitido listar até 50 aventureiros por vez")
            int size
    ) {

        List<AventureiroResumoResponse> aventureiros = aventureiroService.listarComFiltros(classe, ativo, nivelMinimo, page, size);
        long totalCount = aventureiroService.contarAventureirosComFiltros(classe, ativo, nivelMinimo);
        int totalPages = (int) Math.ceil((double) totalCount / size);
        return ResponseEntity.ok()
                .header("X-Page", String.valueOf(page))
                .header("X-Size", String.valueOf(size))
                .header("X-Total-Count", String.valueOf(totalCount))
                .header("X-Total-Pages", String.valueOf(totalPages))
                .body(aventureiros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AventureiroResponse> buscarAventureiro(@PathVariable Long id) {
        AventureiroResponse response = aventureiroService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AventureiroResponse> atualizarAventureiro(
            @PathVariable Long id,
            @Valid @RequestBody AventureiroAtualizacaoRequest request) {
        Aventureiro aventureiro = aventureiroService.atualizarAventureiro(id, request);
        return ResponseEntity.ok(aventureiroMapper.toResponse(aventureiro));
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativarAventureiro(@PathVariable Long id) {
        aventureiroService.encerrarVinculo(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/reativar")
    public ResponseEntity<Void> reativarAventureiro(@PathVariable Long id) {
        aventureiroService.recrutarAventureiro(id);
        return ResponseEntity.noContent().build();
    }

    // GERENCIAMENTO DE COMPANHEIROS

    @PostMapping("/{id}/companheiro")
    public ResponseEntity<AventureiroResponse> invocarCompanheiro(
            @PathVariable Long id,
            @Valid @RequestBody CompanheiroCriacaoRequest request) {
        aventureiroService.invocarCompanheiro(id, request.nome(), request.especie(), request.lealdade());
        AventureiroResponse response = aventureiroService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/companheiro")
    public ResponseEntity<Void> banirCompanheiro(@PathVariable Long id) {
        aventureiroService.banirCompanheiro(id);
        return ResponseEntity.noContent().build();
    }

}
