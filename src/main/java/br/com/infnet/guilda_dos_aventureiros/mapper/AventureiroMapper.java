package br.com.infnet.guilda_dos_aventureiros.mapper;

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
        return new AventureiroResponse(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.isAtivo(),
                Optional.ofNullable(aventureiro.getCompanheiro())
        );
    }
}
