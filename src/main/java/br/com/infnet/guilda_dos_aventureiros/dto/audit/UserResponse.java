package br.com.infnet.guilda_dos_aventureiros.dto.audit;

import jakarta.validation.constraints.NotNull;

public record UserResponse (
        @NotNull
        Long id,
        @NotNull
        String nome
) {}
