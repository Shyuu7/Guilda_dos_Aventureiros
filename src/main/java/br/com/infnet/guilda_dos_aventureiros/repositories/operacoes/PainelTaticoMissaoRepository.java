package br.com.infnet.guilda_dos_aventureiros.repositories.operacoes;

import br.com.infnet.guilda_dos_aventureiros.entities.operacoes.PainelTaticoMissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PainelTaticoMissaoRepository extends JpaRepository<PainelTaticoMissao, Long> {
    List<PainelTaticoMissao> findTop10ByUltimaAtualizacaoBetweenOrderByIndiceProntidaoDesc(LocalDateTime inicio, LocalDateTime fim);
}
