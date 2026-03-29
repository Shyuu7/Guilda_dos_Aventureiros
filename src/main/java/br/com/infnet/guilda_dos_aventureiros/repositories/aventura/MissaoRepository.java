package br.com.infnet.guilda_dos_aventureiros.repositories.aventura;

import br.com.infnet.guilda_dos_aventureiros.dto.aventura.MissaoResumoResponse;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.Missao;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.NivelPerigo;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.StatusMissao;
import br.com.infnet.guilda_dos_aventureiros.dto.relatorios.RelatorioMissaoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

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

    @Query(value = """
        SELECT
            m.titulo as titulo,
            m.status as status,
            m.nivel_perigo as nivelPerigo,
            m.data_inicio as dataInicio,
            COUNT(p.aventureiro_id) as quantidadeParticipantes,
            COALESCE(SUM(p.recompensa_ouro), 0.0) as totalRecompensas
        FROM
            aventura.missoes m
        LEFT JOIN
            aventura.participacoes_missoes p ON m.id = p.missao_id
        WHERE
            m.status <> 'CANCELADA'
            AND m.data_criacao >= COALESCE(:dataMin, '-infinity'::timestamp)
            AND m.data_criacao <= COALESCE(:dataMax, 'infinity'::timestamp)
        GROUP BY
            m.id, m.titulo, m.status, m.nivel_perigo, m.data_inicio
        ORDER BY
            m.data_inicio DESC
        """, nativeQuery = true)
    List<RelatorioMissaoProjection> gerarRelatorioMissoes(
            @Param("dataMin") LocalDateTime dataMin,
            @Param("dataMax") LocalDateTime dataMax
    );
}
