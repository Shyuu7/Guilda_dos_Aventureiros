package br.com.infnet.guilda_dos_aventureiros.repositories.aventura;

import br.com.infnet.guilda_dos_aventureiros.entities.aventura.Missao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissaoRepository extends JpaRepository<Missao, Long> {
}
