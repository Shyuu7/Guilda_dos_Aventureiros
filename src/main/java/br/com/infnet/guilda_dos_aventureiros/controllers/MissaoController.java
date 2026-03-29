package br.com.infnet.guilda_dos_aventureiros.controllers;

import br.com.infnet.guilda_dos_aventureiros.dto.PagedResponse;
import br.com.infnet.guilda_dos_aventureiros.dto.aventura.*;
import br.com.infnet.guilda_dos_aventureiros.service.MissaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/missoes")
@RequiredArgsConstructor
public class MissaoController {

    private final MissaoService missaoService;

    @PostMapping
    public ResponseEntity<MissaoResponse> criarMissao(@Valid @RequestBody MissaoCriacaoRequest request) {
        MissaoResponse novaMissao = missaoService.criarMissao(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMissao);
    }

    @GetMapping()
    public ResponseEntity<PagedResponse<MissaoResumoResponse>> listarMissoesComFiltros(
            MissaoFiltroRequest filtro,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        PagedResponse<MissaoResumoResponse> pagedResponse = missaoService.listar(filtro, pageable);
        return ResponseEntity.ok()
                .header("X-Page", String.valueOf(pagedResponse.page()))
                .header("X-Size", String.valueOf(pagedResponse.size()))
                .header("X-Total-Count", String.valueOf(pagedResponse.total()))
                .header("X-Total-Pages", String.valueOf(pagedResponse.totalPages()))
                .body(pagedResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissaoResponse> obterMissaoPorId(@PathVariable Long id) {
        MissaoResponse missao = missaoService.obterMissaoPorId(id);
        return ResponseEntity.ok(missao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MissaoResponse> atualizarMissao(@PathVariable Long id, @Valid @RequestBody MissaoCriacaoRequest request) {
        MissaoResponse missaoAtualizada = missaoService.atualizarMissao(id, request);
        return ResponseEntity.ok(missaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerMissao(@PathVariable Long id) {
        missaoService.removerMissao(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idMissao}/participantes/{idAventureiro}")
    public ResponseEntity<ParticipacaoResponse> adicionarAventureiro(
            @PathVariable Long idMissao,
            @PathVariable Long idAventureiro,
            @Valid @RequestBody ParticipacaoRequest request) {
        ParticipacaoResponse novaParticipacao = missaoService.adicionarAventureiroNaMissao(idMissao, idAventureiro, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaParticipacao);
    }

    @PutMapping("/{idMissao}/participantes/{idAventureiro}")
    public ResponseEntity<ParticipacaoResponse> atualizarParticipacao(
            @PathVariable Long idMissao,
            @PathVariable Long idAventureiro,
            @Valid @RequestBody ParticipacaoRequest request) {
        ParticipacaoResponse participacaoAtualizada = missaoService.atualizarParticipacaoNaMissao(idMissao, idAventureiro, request);
        return ResponseEntity.ok(participacaoAtualizada);
    }

    @DeleteMapping("/{idMissao}/participantes/{idAventureiro}")
    public ResponseEntity<Void> removerAventureiro(
            @PathVariable Long idMissao,
            @PathVariable Long idAventureiro) {
        missaoService.removerAventureiroDaMissao(idMissao, idAventureiro);
        return ResponseEntity.noContent().build();
    }



}
