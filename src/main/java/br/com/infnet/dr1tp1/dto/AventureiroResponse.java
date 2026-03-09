package br.com.infnet.dr1tp1.dto;

import br.com.infnet.dr1tp1.enums.Classes;
import br.com.infnet.dr1tp1.entity.Companheiro;

import java.util.Optional;

public record AventureiroResponse(
        Long id,
        String nome,
        Classes classe,
        int nivel,
        boolean ativo,
        Optional<Companheiro> companheiro
) {
}
