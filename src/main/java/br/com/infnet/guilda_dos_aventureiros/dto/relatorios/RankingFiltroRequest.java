package br.com.infnet.guilda_dos_aventureiros.dto.relatorios;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.StatusMissao;
import java.time.LocalDateTime;

public record RankingFiltroRequest(
        StatusMissao status,
        LocalDateTime inicio,
        LocalDateTime termino
) {
}
