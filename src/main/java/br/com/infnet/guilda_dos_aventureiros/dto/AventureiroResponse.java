package br.com.infnet.guilda_dos_aventureiros.dto;

import br.com.infnet.guilda_dos_aventureiros.enums.AventureiroClasses;
import br.com.infnet.guilda_dos_aventureiros.entities.Companheiro;

import java.util.Optional;

public record AventureiroResponse(
        Long id,
        String nome,
        AventureiroClasses classe,
        int nivel,
        boolean ativo,
        Optional<Companheiro> companheiro
) {
}
