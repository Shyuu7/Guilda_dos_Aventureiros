package br.com.infnet.guilda_dos_aventureiros.dto.relatorios;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.NivelPerigo;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.StatusMissao;
import java.math.BigDecimal;

public record RelatorioMissaoResponse(
        String titulo,
        StatusMissao status,
        NivelPerigo nivelPerigo,
        Long quantidadeParticipantes,
        BigDecimal totalRecompensas
) {
}
