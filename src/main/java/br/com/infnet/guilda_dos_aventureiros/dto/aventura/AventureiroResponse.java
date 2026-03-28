package br.com.infnet.guilda_dos_aventureiros.dto.aventura;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.AventureiroClasses;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.Companheiro;

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
