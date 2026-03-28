package br.com.infnet.guilda_dos_aventureiros.dto.aventura;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.AventureiroClasses;
import jakarta.validation.constraints.Min;

public record AventureiroFiltroRequest(
        Boolean status,
        AventureiroClasses classe,
        @Min(value = 1, message = "O nível deve ser maior ou igual a 1")
        Integer nivelMinimo
) {
}