package br.com.infnet.guilda_dos_aventureiros.mapper;

import br.com.infnet.guilda_dos_aventureiros.dto.audit.OrganizationResponse;
import br.com.infnet.guilda_dos_aventureiros.dto.audit.UserResponse;
import br.com.infnet.guilda_dos_aventureiros.dto.aventura.AventureiroResumoResponse;
import br.com.infnet.guilda_dos_aventureiros.entities.aventura.Aventureiro;
import br.com.infnet.guilda_dos_aventureiros.dto.aventura.AventureiroCriacaoRequest;
import br.com.infnet.guilda_dos_aventureiros.dto.aventura.AventureiroResponse;
import org.springframework.stereotype.Component;
import java.util.Optional;


@Component
public class AventureiroMapper {
    public Aventureiro toEntity(AventureiroCriacaoRequest request) {
        return new Aventureiro(
                request.nome(),
                request.classe(),
                request.nivel()
        );
    }

    public AventureiroResponse toResponse(Aventureiro aventureiro) {
        OrganizationResponse orgResponse = new OrganizationResponse(
                aventureiro.getOrganizacao().getId(),
                aventureiro.getOrganizacao().getName()
        );

        UserResponse userResponse = new UserResponse(
                aventureiro.getUsuario().getId(),
                aventureiro.getUsuario().getName()
        );

        return new AventureiroResponse(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                orgResponse,
                userResponse,
                aventureiro.isAtivo(),
                Optional.ofNullable(aventureiro.getCompanheiro())
        );
    }

    public AventureiroResumoResponse toResumoResponse(Aventureiro aventureiro) {
        return new AventureiroResumoResponse(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.isAtivo()
        );
    }
}
