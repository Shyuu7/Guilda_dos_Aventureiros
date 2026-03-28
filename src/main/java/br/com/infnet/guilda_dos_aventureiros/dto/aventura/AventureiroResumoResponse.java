package br.com.infnet.guilda_dos_aventureiros.dto.aventura;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.AventureiroClasses;

public record AventureiroResumoResponse(
        Long id,
        String nome,
        AventureiroClasses classe,
        int nivel,
        boolean ativo
) {
}
