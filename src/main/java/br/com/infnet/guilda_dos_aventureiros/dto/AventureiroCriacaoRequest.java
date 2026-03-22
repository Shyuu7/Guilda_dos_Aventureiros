package br.com.infnet.guilda_dos_aventureiros.dto;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.AventureiroClasses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record AventureiroCriacaoRequest(
        @NotBlank (message = "Nome é obrigatório e não pode estar em branco")
        String nome,
        @Valid AventureiroClasses classe,
        @Positive @Min(value = 1, message = "O nível precisa ser maior ou igual a 1.")
        int nivel
        ) {
}
