package br.com.infnet.guilda_dos_aventureiros.dto.aventura;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.NivelPerigo;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.StatusMissao;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.List;

@JsonPropertyOrder({"id", "organizacaoId", "titulo", "nivelPerigo", "status", "dataInicio", "dataTermino", "participacoes"})
public record MissaoResponse (
    Long id,
    String titulo,
    NivelPerigo nivelPerigo,
    StatusMissao status,
    LocalDateTime dataInicio,
    LocalDateTime dataTermino,
    Long organizacaoId,
    List<ParticipacaoResponse> participacoes
    ){}

