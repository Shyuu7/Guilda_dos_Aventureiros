package br.com.infnet.guilda_dos_aventureiros.dto.aventura;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.NivelPerigo;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.StatusMissao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;


public record MissaoCriacaoRequest(
    @NotBlank
    String titulo,

    @NotNull
    NivelPerigo nivelPerigo,

    @NotNull
    StatusMissao status,

    LocalDateTime dataInicio,

    LocalDateTime dataTermino,

    @NotNull
    Long organizacaoId
){}
