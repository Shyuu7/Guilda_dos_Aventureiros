package br.com.infnet.dr1tp1.domain;

import br.com.infnet.dr1tp1.enums.Especies;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class Companheiro {
    private String nome;
    @Valid @NotNull(message = "A espécie do companheiro é obrigatória")
    private Especies especie;
    @PositiveOrZero(groups = int.class)
    @Range(min = 0, max = 100, message = "A lealdade do companheiro deve ser um número inteiro entre 0 e 100")
    private int lealdade;

    public Companheiro(String nome, Especies especie, int lealdade) {
        this.nome = nome;
        this.especie = especie;
        this.lealdade = lealdade;
    }
}
