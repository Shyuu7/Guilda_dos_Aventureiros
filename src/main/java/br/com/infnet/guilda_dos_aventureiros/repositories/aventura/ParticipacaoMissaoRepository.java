package br.com.infnet.guilda_dos_aventureiros.repositories.aventura;

import br.com.infnet.guilda_dos_aventureiros.entities.aventura.ParticipacaoMissao;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.ParticipacaoMissaoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipacaoMissaoRepository extends JpaRepository<ParticipacaoMissao, ParticipacaoMissaoId> {
}
