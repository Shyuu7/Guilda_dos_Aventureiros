package br.com.infnet.guilda_dos_aventureiros.controllers.operacoes;

import br.com.infnet.guilda_dos_aventureiros.entities.operacoes.PainelTaticoMissao;
import br.com.infnet.guilda_dos_aventureiros.service.operacoes.PainelTaticoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/painel-tatico")
public class PainelTaticoController {

    private final PainelTaticoService painelTaticoService;

    @GetMapping("/top15dias")
    public ResponseEntity<List<PainelTaticoMissao>> getTopMissoesTaticas() {
        List<PainelTaticoMissao> missoes = painelTaticoService.findTopMissoesUltimos15Dias();
        return ResponseEntity.ok(missoes);
    }
}
