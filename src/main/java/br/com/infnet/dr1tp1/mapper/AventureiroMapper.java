package br.com.infnet.dr1tp1.mapper;

import br.com.infnet.dr1tp1.entity.Aventureiro;
import br.com.infnet.dr1tp1.dto.AventureiroCriacaoRequest;
import br.com.infnet.dr1tp1.dto.AventureiroResponse;
import org.springframework.stereotype.Component;


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
                aventureiro.getCompanheiro()
        );
    }
}
