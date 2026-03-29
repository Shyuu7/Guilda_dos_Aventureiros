package br.com.infnet.guilda_dos_aventureiros.controllers;

import br.com.infnet.guilda_dos_aventureiros.dto.*;
import br.com.infnet.guilda_dos_aventureiros.dto.aventura.*;
import br.com.infnet.guilda_dos_aventureiros.service.AventureiroService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import javax.swing.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/aventureiros")
public class AventureiroController {
    private final AventureiroService aventureiroService;

    @PostMapping
    public ResponseEntity<AventureiroResponse> criarAventureiro(
            @Valid @RequestBody AventureiroCriacaoRequest request) {
        AventureiroResponse response = aventureiroService.criarAventureiro(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AventureiroResponse>> listarTodosAventureiros()
    {
        List<AventureiroResponse> aventureiros = aventureiroService.findAllSortedById();
        return ResponseEntity.ok(aventureiros);
    }

    @GetMapping("/filtro")
    public ResponseEntity<PagedResponse<AventureiroResumoResponse>> listarAventureirosComFiltros(
            @Valid AventureiroFiltroRequest filtro,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        PagedResponse<AventureiroResumoResponse> pagedResponse = aventureiroService.listarComFiltros(filtro, pageable);
        return ResponseEntity.ok()
                .header("X-Page", String.valueOf(pagedResponse.page()))
                .header("X-Size", String.valueOf(pagedResponse.size()))
                .header("X-Total-Count", String.valueOf(pagedResponse.total()))
                .header("X-Total-Pages", String.valueOf(pagedResponse.totalPages()))
                .body(pagedResponse);
    }

    @GetMapping("/buscar")
    public ResponseEntity<PagedResponse<AventureiroResumoResponse>> buscarPorNome(
            @RequestParam String nome,
            Pageable pageable
    ) {
        PagedResponse<AventureiroResumoResponse> pagedResponse = aventureiroService.buscarPorNome(nome, pageable);
        return ResponseEntity.ok(pagedResponse);
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
        AventureiroResponse response = aventureiroService.atualizarAventureiro(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativarAventureiro(@PathVariable Long id) {
        aventureiroService.encerrarVinculo(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reativar")
    public ResponseEntity<Void> reativarAventureiro(@PathVariable Long id) {
        aventureiroService.recrutarAventureiro(id);
        return ResponseEntity.noContent().build();
    }

    // --- GERENCIAMENTO DE COMPANHEIROS ---
    @PostMapping("/{id}/companheiro")
    public ResponseEntity<AventureiroResponse> invocarCompanheiro(
            @PathVariable Long id,
            @Valid @RequestBody CompanheiroCriacaoRequest request) {
        AventureiroResponse response = aventureiroService.invocarCompanheiro(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/companheiro")
    public ResponseEntity<Void> banirCompanheiro(@PathVariable Long id) {
        aventureiroService.banirCompanheiro(id);
        return ResponseEntity.noContent().build();
    }
}
