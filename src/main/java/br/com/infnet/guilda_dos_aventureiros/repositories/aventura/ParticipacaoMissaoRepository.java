package br.com.infnet.guilda_dos_aventureiros.repositories.aventura;

import br.com.infnet.guilda_dos_aventureiros.entities.aventura.ParticipacaoMissao;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.ParticipacaoMissaoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipacaoMissaoRepository extends JpaRepository<ParticipacaoMissao, ParticipacaoMissaoId> {
    long countByAventureiroId(Long aventureiroId);
    Optional<ParticipacaoMissao> findFirstByAventureiroIdOrderByDataDeRegistroDesc(Long aventureiroId);
}
