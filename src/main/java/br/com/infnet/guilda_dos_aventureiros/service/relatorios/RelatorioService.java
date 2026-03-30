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

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RelatorioService {

    private final MissaoRepository missaoRepository;
    private final ParticipacaoMissaoRepository participacaoMissaoRepository;

    public List<RelatorioMissaoProjection> gerarRelatorioMissoes(RelatorioFiltroRequest filtro) {
        LocalDateTime dataInicio = filtro.inicio() != null
                ? filtro.inicio()
                : LocalDateTime.of(1900, 1, 1, 0, 0);

        LocalDateTime dataFim = filtro.termino() != null
                ? filtro.termino()
                : LocalDateTime.of(2099, 12, 31, 23, 59);
        return missaoRepository.gerarRelatorioMissoes(
                dataInicio,
                dataFim
        );
    }

    public List<RankingProjection> gerarRankingAventureiros(RankingFiltroRequest filtro) {
        String statusAsString = (filtro.status() != null) ? filtro.status().name() : null;
        LocalDateTime dataInicio = filtro.inicio() != null
                ? filtro.inicio()
                : LocalDateTime.of(1900, 1, 1, 0, 0);

        LocalDateTime dataFim = filtro.termino() != null
                ? filtro.termino()
                : LocalDateTime.of(2099, 12, 31, 23, 59);
        return participacaoMissaoRepository.gerarRankingAventureiros(
                dataInicio,
                dataFim,
                statusAsString
        );
    }
}
