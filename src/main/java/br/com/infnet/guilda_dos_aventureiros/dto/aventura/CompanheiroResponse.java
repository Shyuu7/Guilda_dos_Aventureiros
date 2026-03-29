package br.com.infnet.guilda_dos_aventureiros.dto.aventura;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.CompanheiroEspecies;

public record CompanheiroResponse(
        Long id,
        String nome,
        CompanheiroEspecies especie,
        int lealdade
) {
}
