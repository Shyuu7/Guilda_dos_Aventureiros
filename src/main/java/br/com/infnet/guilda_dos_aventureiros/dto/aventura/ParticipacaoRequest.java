package br.com.infnet.guilda_dos_aventureiros.dto.aventura;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.PapelMissao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParticipacaoRequest {
    @NotNull
    private PapelMissao papelMissao;
    @PositiveOrZero
    private BigDecimal recompensaEmOuro;
    @NotNull
    private boolean destaque;
}
