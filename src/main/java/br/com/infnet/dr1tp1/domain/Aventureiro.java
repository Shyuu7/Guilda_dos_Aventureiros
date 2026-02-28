package br.com.infnet.dr1tp1.domain;

import br.com.infnet.dr1tp1.enums.Classes;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
public class Aventureiro {
    private Long id;
    @NotBlank(message = "O nome do aventureiro é obrigatório")
    private String nome;
    @NotNull(message = "A classe do aventureiro é obrigatória")
    private Classes classe;
    @Min(value = 1, message = "O nível do aventureiro deve ser no mínimo 1")
    private int nivel;
    @NotNull
    private boolean ativo;
    private Optional<Companheiro> companheiro;

    public Aventureiro(String nome, Classes classe, int nivel) {
        final AtomicLong idGenerator = new AtomicLong(1);
        this.id = idGenerator.getAndIncrement();
        this.nome = nome;
        this.classe = classe;
        this.nivel = nivel;
        this.ativo = true;
    }

    public void recrutar() {
        this.ativo = true;
    }

    public void encerrarVinculo() {
        this.ativo = false;
    }
}
