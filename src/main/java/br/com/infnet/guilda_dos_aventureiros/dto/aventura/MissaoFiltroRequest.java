package br.com.infnet.guilda_dos_aventureiros.dto.aventura;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.NivelPerigo;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.StatusMissao;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.TipoDataMissao;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

public record MissaoFiltroRequest(
        StatusMissao status,
        NivelPerigo nivelPerigo,
        TipoDataMissao tipoData,
        @PastOrPresent (message = "A data mínima deve ser no passado ou presente")
        LocalDateTime dataMin,
        @PastOrPresent (message = "A data máxima deve ser no passado ou presente")
        LocalDateTime dataMax
) {}
