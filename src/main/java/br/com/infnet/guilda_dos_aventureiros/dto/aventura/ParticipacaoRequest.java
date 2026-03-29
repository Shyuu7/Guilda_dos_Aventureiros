package br.com.infnet.guilda_dos_aventureiros.dto.aventura;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.PapelMissao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;


public record ParticipacaoRequest(
    @NotNull
    PapelMissao papelMissao,

    @PositiveOrZero
    BigDecimal recompensaEmOuro,

    @NotNull
    boolean destaque
){}

