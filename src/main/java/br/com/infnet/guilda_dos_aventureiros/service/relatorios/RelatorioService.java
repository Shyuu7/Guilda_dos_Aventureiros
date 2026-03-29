package br.com.infnet.guilda_dos_aventureiros.service.relatorios;

import br.com.infnet.guilda_dos_aventureiros.dto.relatorios.RankingFiltroRequest;
import br.com.infnet.guilda_dos_aventureiros.dto.relatorios.RankingProjection;
import br.com.infnet.guilda_dos_aventureiros.dto.relatorios.RelatorioFiltroRequest;
import br.com.infnet.guilda_dos_aventureiros.dto.relatorios.RelatorioMissaoProjection;
import br.com.infnet.guilda_dos_aventureiros.repositories.aventura.MissaoRepository;
import br.com.infnet.guilda_dos_aventureiros.repositories.aventura.ParticipacaoMissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RelatorioService {

    private final MissaoRepository missaoRepository;
    private final ParticipacaoMissaoRepository participacaoMissaoRepository;

    public List<RelatorioMissaoProjection> gerarRelatorioMissoes(RelatorioFiltroRequest filtro) {
        return missaoRepository.gerarRelatorioMissoes(
                filtro.inicio(),
                filtro.termino()
        );
    }

    public List<RankingProjection> gerarRankingAventureiros(RankingFiltroRequest filtro) {
        String statusAsString = (filtro.status() != null) ? filtro.status().name() : null;
        return participacaoMissaoRepository.gerarRankingAventureiros(
                filtro.inicio(),
                filtro.termino(),
                statusAsString
        );
    }
}
