package br.com.infnet.dr1tp1.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record CompanheiroCriacaoRequest(
        @NotBlank(message = "Nome é obrigatório e não pode ser vazio")
        String nome,
        @Valid String especie,
        @PositiveOrZero @Max(value = 100, message = "Lealdade deve ser um número inteiro entre 0 e 100")
        int lealdade
) {
}
