package br.com.infnet.dr1tp1.dto;

import br.com.infnet.dr1tp1.enums.Classes;

public record AventureiroResumoResponse(
        String nome,
        Classes classe,
        int nivel
) {
}
