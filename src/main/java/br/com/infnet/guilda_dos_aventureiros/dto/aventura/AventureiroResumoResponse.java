package br.com.infnet.guilda_dos_aventureiros.dto.aventura;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.AventureiroClasses;
import jakarta.validation.constraints.NotNull;

public record AventureiroResumoResponse(
        @NotNull
        Long id,
        String nome,
        @NotNull
        AventureiroClasses classe,
        int nivel,
        @NotNull
        boolean ativo
) {
}
