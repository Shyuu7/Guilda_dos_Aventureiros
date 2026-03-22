package br.com.infnet.guilda_dos_aventureiros.dto;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.CompanheiroEspecies;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Range;

public record CompanheiroCriacaoRequest(
        @NotBlank(message = "Nome é obrigatório e não pode ser vazio")
        String nome,
        @Valid @NotNull(message = "A espécie do companheiro é obrigatória")
        CompanheiroEspecies especie,
        @PositiveOrZero(groups = int.class)
        @Range(min = 0, max = 100, message = "A lealdade do companheiro deve ser um número inteiro entre 0 e 100")
        int lealdade
) {
}
