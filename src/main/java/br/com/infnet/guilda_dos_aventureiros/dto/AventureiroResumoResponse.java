package br.com.infnet.guilda_dos_aventureiros.dto;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.AventureiroClasses;

public record AventureiroResumoResponse(
        String nome,
        AventureiroClasses classe,
        int nivel
) {
}
