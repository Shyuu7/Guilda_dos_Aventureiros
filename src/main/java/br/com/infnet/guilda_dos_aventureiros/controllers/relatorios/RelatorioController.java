package br.com.infnet.guilda_dos_aventureiros.controllers.relatorios;

import br.com.infnet.guilda_dos_aventureiros.dto.relatorios.RankingFiltroRequest;
import br.com.infnet.guilda_dos_aventureiros.dto.relatorios.RankingProjection;
import br.com.infnet.guilda_dos_aventureiros.dto.relatorios.RelatorioFiltroRequest;
import br.com.infnet.guilda_dos_aventureiros.dto.relatorios.RelatorioMissaoProjection;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.StatusMissao;
import br.com.infnet.guilda_dos_aventureiros.service.relatorios.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;

    @GetMapping("/ranking")
    public ResponseEntity<List<RankingProjection>> gerarRankingAventureiros(
            @RequestParam(required = false) StatusMissao status,
            @RequestParam(required = false) LocalDateTime inicio,
            @RequestParam(required = false) LocalDateTime termino
    ) {
        RankingFiltroRequest filtro = new RankingFiltroRequest(status, inicio, termino);
        List<RankingProjection> ranking = relatorioService.gerarRankingAventureiros(filtro);
        return ResponseEntity.ok(ranking);
    }

    @GetMapping("/missoes")
    public ResponseEntity<List<RelatorioMissaoProjection>> gerarRelatorioMissoes(
            @RequestParam(required = false) LocalDateTime inicio,
            @RequestParam(required = false) LocalDateTime termino
    ) {
        RelatorioFiltroRequest filtro = new RelatorioFiltroRequest(inicio, termino);
        List<RelatorioMissaoProjection> relatorio = relatorioService.gerarRelatorioMissoes(filtro);
        return ResponseEntity.ok(relatorio);
    }
}
