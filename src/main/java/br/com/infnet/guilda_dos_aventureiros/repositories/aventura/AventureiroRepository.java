package br.com.infnet.guilda_dos_aventureiros.repositories.aventura;

import br.com.infnet.guilda_dos_aventureiros.dto.aventura.AventureiroResumoResponse;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.Aventureiro;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.AventureiroClasses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {
    @Query(value = """
            SELECT new br.com.infnet.guilda_dos_aventureiros.dto.aventura.AventureiroResumoResponse
                        (a.id, a.nome, a.classe, a.nivel, a.ativo)
            FROM Aventureiro a
            WHERE (:status IS NULL OR a.ativo = :status) AND
            (:pClasse IS NULL OR a.classe = :pClasse) AND
            (:nivelMinimo IS NULL OR a.nivel >= :nivelMinimo)
            """)
    Page<AventureiroResumoResponse> findByStatusAndClasseAndNivelMinimo(
            @Param("status") Boolean status,
            @Param("pClasse") AventureiroClasses classe,
            @Param("nivelMinimo") Integer nivelMinimo,
            Pageable pageable
    );

    Page<Aventureiro> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

}
