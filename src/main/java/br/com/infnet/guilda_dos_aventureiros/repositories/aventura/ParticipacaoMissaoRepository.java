package br.com.infnet.guilda_dos_aventureiros.repositories.aventura;

import br.com.infnet.guilda_dos_aventureiros.dto.relatorios.RankingProjection;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.ParticipacaoMissao;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.ParticipacaoMissaoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ParticipacaoMissaoRepository extends JpaRepository<ParticipacaoMissao, ParticipacaoMissaoId> {
    long countByAventureiroId(Long aventureiroId);
    Optional<ParticipacaoMissao> findFirstByAventureiroIdOrderByDataDeRegistroDesc(Long aventureiroId);

    @Query(value = """
        SELECT
            a.nome AS nomeAventureiro,
            COUNT(p.missao_id) AS totalParticipacoes,
            COALESCE(SUM(p.recompensa_ouro), 0.0) AS totalRecompensas,
            SUM(CASE WHEN p.destaque = true THEN 1 ELSE 0 END) AS totalDestaques
        FROM
            aventura.participacoes_missoes p
        JOIN
            aventura.aventureiros a ON p.aventureiro_id = a.id
        JOIN
            aventura.missoes m ON p.missao_id = m.id
        WHERE
            (:status IS NULL OR m.status = CAST(:status AS text))
            AND
            p.data_registro >= COALESCE(:inicio, '-infinity'::timestamp)
            AND
            p.data_registro <= COALESCE(:termino, 'infinity'::timestamp)
        GROUP BY
            a.id, a.nome
        ORDER BY
            totalParticipacoes DESC, totalRecompensas DESC, totalDestaques DESC
        """, nativeQuery = true)
    List<RankingProjection> gerarRankingAventureiros(
            @Param("inicio") LocalDateTime inicio,
            @Param("termino") LocalDateTime termino,
            @Param("status") String status
    );
}
