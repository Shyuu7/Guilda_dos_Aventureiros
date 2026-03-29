package br.com.infnet.guilda_dos_aventureiros.repositories.aventura;

import br.com.infnet.guilda_dos_aventureiros.dto.aventura.MissaoResumoResponse;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.Missao;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.NivelPerigo;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.StatusMissao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MissaoRepository extends JpaRepository<Missao, Long> {

    @Query(value = """
            SELECT m FROM Missao m
            WHERE (:status IS NULL OR m.status = :status)
            AND (:nivelPerigo IS NULL OR m.nivelPerigo = :nivelPerigo)
            AND (
                    (:tipoData IS NULL) OR
                    (:tipoData = 'INICIO' AND m.dataInicio BETWEEN :dataMin AND :dataMax) OR
                    (:tipoData = 'TERMINO' AND m.dataTermino BETWEEN :dataMin AND :dataMax)
            )
            """)
    Page<MissaoResumoResponse> findMissoesByFilters(
            @Param(value = "status") StatusMissao status,
            @Param(value = "nivelPerigo") NivelPerigo nivelPerigo,
            @Param("tipoData") String tipoData,
            @Param("dataMin") LocalDateTime dataMin,
            @Param("dataMax") LocalDateTime dataMax,
            Pageable pageable
    );
}
