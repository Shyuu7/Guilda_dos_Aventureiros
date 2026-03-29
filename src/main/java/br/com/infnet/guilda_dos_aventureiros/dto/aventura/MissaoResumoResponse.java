package br.com.infnet.guilda_dos_aventureiros.dto.aventura;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.NivelPerigo;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.StatusMissao;
import java.time.LocalDateTime;

public record MissaoResumoResponse(
        Long id,
        String titulo,
        StatusMissao status,
        NivelPerigo nivelPerigo,
        LocalDateTime dataInicio,
        LocalDateTime dataTermino
) {}
