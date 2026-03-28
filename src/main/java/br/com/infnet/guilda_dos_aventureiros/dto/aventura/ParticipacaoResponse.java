package br.com.infnet.guilda_dos_aventureiros.dto.aventura;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.PapelMissao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ParticipacaoResponse {
    @NotNull
    private Long aventureiroId;
    @Length(min = 1, max = 120)
    private String nomeAventureiro;
    @NotNull
    private PapelMissao papelMissao;
    @PositiveOrZero
    private BigDecimal recompensaEmOuro;
    @NotNull
    private boolean destaque;
    private LocalDateTime dataDeRegistro;
}
