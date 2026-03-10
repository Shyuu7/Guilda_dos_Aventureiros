package br.com.infnet.guilda_dos_aventureiros.dto;

import br.com.infnet.guilda_dos_aventureiros.enums.Classes;

public record AventureiroResumoResponse(
        String nome,
        Classes classe,
        int nivel
) {
}
