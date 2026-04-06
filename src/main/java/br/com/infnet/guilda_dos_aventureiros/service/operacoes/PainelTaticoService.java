package br.com.infnet.guilda_dos_aventureiros.service.operacoes;
import br.com.infnet.guilda_dos_aventureiros.entities.operacoes.PainelTaticoMissao;
import br.com.infnet.guilda_dos_aventureiros.repositories.operacoes.PainelTaticoMissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PainelTaticoService {
    private final PainelTaticoMissaoRepository painelTaticoMissaoRepository;

    public List<PainelTaticoMissao> findTopMissoesUltimos15Dias() {
        LocalDateTime fim = LocalDateTime.now();
        LocalDateTime inicio = fim.minusDays(15);
        return painelTaticoMissaoRepository.findTop10ByUltimaAtualizacaoBetweenOrderByIndiceProntidaoDesc(inicio, fim);
    }
}
