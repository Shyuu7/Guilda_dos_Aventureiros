package br.com.infnet.guilda_dos_aventureiros.repositories.aventura;

import br.com.infnet.guilda_dos_aventureiros.config.TestConfig;
import br.com.infnet.guilda_dos_aventureiros.dto.aventura.AventureiroResumoResponse;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.Aventureiro;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.AventureiroClasses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestConfig.class)
class AventureiroRepositoryTest {

    @Autowired
    private AventureiroRepository aventureiroRepository;

    @Test
    @DisplayName("Deve buscar aventureiro por ID com sucesso")
    void findByIdTest() {
        Optional<Aventureiro> aventureiroOpt = aventureiroRepository.findById(1L);
        assertThat(aventureiroOpt).isPresent();
        assertThat(aventureiroOpt.get().getNome()).isEqualTo("Aragorn");
    }

    @Test
    @DisplayName("Deve buscar aventureiro por nome (parcial e case-insensitive)")
    void findByNomeContainingIgnoreCaseTest() {
        // Act
        Page<Aventureiro> resultado = aventureiroRepository.findByNomeContainingIgnoreCase("gorn", PageRequest.of(0, 10));

        // Assert
        assertThat(resultado.getTotalElements()).isEqualTo(1);
        assertThat(resultado.getContent().getFirst().getNome()).isEqualTo("Aragorn");
    }

    @Test
    @DisplayName("Deve listar aventureiros com filtro por classe e status")
    void findByFiltrosTest() {
        // Act
        Page<AventureiroResumoResponse> guerreirosAtivos = aventureiroRepository.findByStatusAndClasseAndNivelMinimo(true, AventureiroClasses.GUERREIRO, null, PageRequest.of(0, 10));
        Page<AventureiroResumoResponse> todosInativos = aventureiroRepository.findByStatusAndClasseAndNivelMinimo(false, null, null, PageRequest.of(0, 10));

        // Assert
        assertThat(guerreirosAtivos.getTotalElements()).isEqualTo(32L);
        assertThat(guerreirosAtivos.getContent().getFirst().nome()).isEqualTo("Aragorn");

        assertThat(todosInativos.getTotalElements()).isEqualTo(8L);
        assertThat(todosInativos.getContent().getFirst().nome()).isEqualTo("Lydia");
    }
}
