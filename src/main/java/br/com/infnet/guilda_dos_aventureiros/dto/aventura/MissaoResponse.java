package br.com.infnet.guilda_dos_aventureiros.dto.aventura;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.NivelPerigo;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.StatusMissao;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonPropertyOrder({"id", "organizacaoId", "titulo", "nivelPerigo", "status", "dataInicio", "dataTermino", "participacoes"})
public class MissaoResponse {
    @NotNull
    private Long id;
    @Length(min = 1, max = 150)
    private String titulo;
    @NotNull
    private NivelPerigo nivelPerigo;
    @NotNull
    private StatusMissao status;
    private LocalDateTime dataInicio;
    private LocalDateTime dataTermino;
    private Long organizacaoId;
    private List<ParticipacaoResponse> participacoes;
}
