package br.com.infnet.guilda_dos_aventureiros.controllers.operacoes;

import br.com.infnet.guilda_dos_aventureiros.dto.operacoes.PainelTaticoMissaoDTO;
import br.com.infnet.guilda_dos_aventureiros.service.operacoes.PainelTaticoMissaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/painel-tatico")
public class PainelTaticoMissaoController {

    private final PainelTaticoMissaoService painelTaticoMissaoService;

    @GetMapping("/top15dias")
    public ResponseEntity<List<PainelTaticoMissaoDTO>> getTopMissoesTaticas() {
        List<PainelTaticoMissaoDTO> missoes = painelTaticoMissaoService.findTopMissoesUltimos15Dias();
        return ResponseEntity.ok(missoes);
    }
}
