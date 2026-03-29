package br.com.infnet.guilda_dos_aventureiros.dto.aventura;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.PapelMissao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ParticipacaoResponse (
    Long aventureiroId,
    String nomeAventureiro,
    PapelMissao papelMissao,
    BigDecimal recompensaEmOuro,
    boolean destaque,
    LocalDateTime dataDeRegistro
    ) {}
