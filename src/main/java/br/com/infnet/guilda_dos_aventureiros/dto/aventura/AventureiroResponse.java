package br.com.infnet.guilda_dos_aventureiros.dto.aventura;

import br.com.infnet.guilda_dos_aventureiros.dto.audit.OrganizationResponse;
import br.com.infnet.guilda_dos_aventureiros.dto.audit.UserResponse;
import br.com.infnet.guilda_dos_aventureiros.entities.audit.Organization;
import br.com.infnet.guilda_dos_aventureiros.entities.audit.User;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.AventureiroClasses;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.Companheiro;

import java.util.Optional;

public record AventureiroResponse(
        Long id,
        String nome,
        AventureiroClasses classe,
        int nivel,
        OrganizationResponse organizacao,
        UserResponse usuario,
        boolean ativo,
        Optional<Companheiro> companheiro
) {
}
