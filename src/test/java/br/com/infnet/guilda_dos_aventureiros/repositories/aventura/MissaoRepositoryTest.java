package br.com.infnet.guilda_dos_aventureiros.repositories.aventura;

import br.com.infnet.guilda_dos_aventureiros.config.TestConfig;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.Missao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
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
        // Act
        // O ID 10 foi inserido pelo script V5__Insert_Seed_Data.sql
        Optional<Missao> missaoOpt = missaoRepository.findById(10L);

        // Assert
        assertThat(missaoOpt).isPresent();
        Missao missao = missaoOpt.get();
        assertThat(missao.getTitulo()).isEqualTo("Destruir o Altar Necromântico");
        assertThat(missao.getParticipacoes()).hasSize(2);
    }
}
