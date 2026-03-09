package br.com.infnet.dr1tp1.entity;

import br.com.infnet.dr1tp1.enums.Especies;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Companheiro {
    private String nome;
    private Especies especie;
    private int lealdade;

    public Companheiro(String nome, Especies especie, int lealdade) {
        this.nome = nome;
        this.especie = especie;
        this.lealdade = lealdade;
    }
}
