package br.com.infnet.guilda_dos_aventureiros.repositories.aventura;

import br.com.infnet.guilda_dos_aventureiros.config.TestConfig;
import br.com.infnet.guilda_dos_aventureiros.dto.relatorios.RankingProjection;
import br.com.infnet.guilda_dos_aventureiros.dto.relatorios.RelatorioMissaoProjection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestConfig.class)
class RelatoriosRepositoryTest {

    @Autowired
    private MissaoRepository missaoRepository;

    @Autowired
    private ParticipacaoMissaoRepository participacaoMissaoRepository;

    @Test
    @DisplayName("Deve gerar relatório de missões com métricas agregadas")
    void gerarRelatorioMissoesTest() {
        LocalDateTime inicio = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 12, 31, 23, 59);

        List<RelatorioMissaoProjection> relatorio = missaoRepository.gerarRelatorioMissoes(inicio, fim);

        assertThat(relatorio).isNotEmpty();

        RelatorioMissaoProjection missaoProtegerVila = relatorio.stream()
                .filter(r -> r.getTitulo().equals("Destruir o Altar Necromântico"))
                .findFirst().orElse(null);
        assertThat(missaoProtegerVila).isNotNull();
        assertThat(missaoProtegerVila.getQuantidadeParticipantes()).isEqualTo(2);
        assertThat(missaoProtegerVila.getTotalRecompensas()).isEqualByComparingTo(new BigDecimal("18000.00"));
    }

    @Test
    @DisplayName("Deve gerar ranking de aventureiros corretamente")
    void gerarRankingAventureirosTest() {
        // Arrange
        LocalDateTime inicio = LocalDateTime.of(1900, 1, 1, 0, 0);
        LocalDateTime fim = LocalDateTime.of(2099, 12, 31, 23, 59);

        // Act
        List<RankingProjection> ranking = participacaoMissaoRepository.gerarRankingAventureiros(inicio, fim, null);

        // Assert
        assertThat(ranking).isNotEmpty();

        // Verifica o primeiro do ranking
        RankingProjection primeiroLugar = ranking.getFirst();
        assertThat(primeiroLugar.getNomeAventureiro()).isEqualTo("Aragorn");
        assertThat(primeiroLugar.getTotalRecompensas()).isEqualByComparingTo(new BigDecimal("3500.00"));
        assertThat(primeiroLugar.getTotalParticipacoes()).isEqualTo(2);
    }
}
