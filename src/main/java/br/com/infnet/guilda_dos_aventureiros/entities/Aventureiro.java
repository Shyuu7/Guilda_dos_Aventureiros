package br.com.infnet.guilda_dos_aventureiros.entities;

import br.com.infnet.guilda_dos_aventureiros.enums.Classes;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
public class Aventureiro {
    private Long id;
    private String nome;
    private Classes classe;
    private int nivel;
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
