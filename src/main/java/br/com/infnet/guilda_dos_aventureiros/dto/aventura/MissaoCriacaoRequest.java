package br.com.infnet.guilda_dos_aventureiros.dto.aventura;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.NivelPerigo;
import br.com.infnet.guilda_dos_aventureiros.enums.aventura.StatusMissao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MissaoCriacaoRequest {
    @NotBlank
    private String titulo;

    @NotNull
    private NivelPerigo nivelPerigo;

    @NotNull
    private StatusMissao status;

    private LocalDateTime dataInicio;

    private LocalDateTime dataTermino;

    @NotNull
    private Long organizacaoId;
}
