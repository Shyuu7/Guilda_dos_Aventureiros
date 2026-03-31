package br.com.infnet.guilda_dos_aventureiros.repositories.aventura;

import br.com.infnet.guilda_dos_aventureiros.config.TestConfig;
import br.com.infnet.guilda_dos_aventureiros.dto.aventura.MissaoResumoResponse;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.Missao;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.NivelPerigo;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.StatusMissao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestConfig.class)
@Transactional
class MissaoRepositoryTest {

    @Autowired
    private MissaoRepository missaoRepository;

    @Test
    @DisplayName("Deve buscar missão por ID e carregar participações")
    void findByIdWithParticipacoesTest() {
        Optional<Missao> missaoOpt = missaoRepository.findById(10L);

        assertThat(missaoOpt).isPresent();
        Missao missao = missaoOpt.get();
        assertThat(missao.getTitulo()).isEqualTo("Destruir o Altar Necromântico");
        assertThat(missao.getParticipacoes()).hasSize(2);
    }

    @Test
    @DisplayName("Deve listar missões com filtros de forma paginada")
    void findMissoesByFiltersTest() {
        Pageable pageable = PageRequest.of(0, 20);

        // --- Cenário 1: Filtrar por Status 'CONCLUIDA' ---
        Page<MissaoResumoResponse> missoesConcluidas = missaoRepository.findMissoesByFilters(
                StatusMissao.CONCLUIDA, null, null, null, null, pageable
        );

        assertThat(missoesConcluidas).isNotNull();
        assertThat(missoesConcluidas.getTotalElements()).isEqualTo(29);
        assertThat(missoesConcluidas.getContent().getFirst().titulo()).isEqualTo("Escolta do Comerciante de Especiarias");

        // --- Cenário 2: Filtrar por NivelPerigo 'CRITICO' ---
        Page<MissaoResumoResponse> missoesCriticas = missaoRepository.findMissoesByFilters(
                null, NivelPerigo.CRITICO, null, null, null, pageable
        );

        assertThat(missoesCriticas).isNotNull();
        assertThat(missoesCriticas.getTotalElements()).isEqualTo(15);
        assertThat(missoesCriticas.getContent().getFirst().titulo()).isEqualTo("Encontrar a Fonte da Juventude");
    }
}
