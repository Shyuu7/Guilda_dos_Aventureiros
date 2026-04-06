package br.com.infnet.guilda_dos_aventureiros.service.operacoes;
import br.com.infnet.guilda_dos_aventureiros.dto.operacoes.PainelTaticoMissaoDTO;
import br.com.infnet.guilda_dos_aventureiros.entities.operacoes.PainelTaticoMissao;
import br.com.infnet.guilda_dos_aventureiros.mapper.PainelTaticoMissaoMapper;
import br.com.infnet.guilda_dos_aventureiros.repositories.operacoes.PainelTaticoMissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PainelTaticoMissaoService {
    private final PainelTaticoMissaoRepository painelTaticoMissaoRepository;
    private final PainelTaticoMissaoMapper painelTaticoMissaoMapper;

    @Cacheable("topMissoes")
    public List<PainelTaticoMissaoDTO> findTopMissoesUltimos15Dias() {
        LocalDateTime fim = LocalDateTime.now();
        LocalDateTime inicio = fim.minusDays(15);
        List<PainelTaticoMissao> missoes = painelTaticoMissaoRepository.findTop10ByUltimaAtualizacaoBetweenOrderByIndiceProntidaoDesc(inicio, fim);
        return missoes.stream()
                .map(painelTaticoMissaoMapper::toDto)
                .toList();
    }
}
