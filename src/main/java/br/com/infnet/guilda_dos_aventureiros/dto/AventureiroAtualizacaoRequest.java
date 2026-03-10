package br.com.infnet.guilda_dos_aventureiros.dto;

import br.com.infnet.guilda_dos_aventureiros.enums.Classes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record AventureiroAtualizacaoRequest(
        @NotBlank
        String nome,
        @NotNull
        Classes classe,
        @Min(1) @Positive
        int nivel
) {}
