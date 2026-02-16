package br.com.infnet.dr1tp1.model;

import br.com.infnet.dr1tp1.enums.Classes;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class Aventureiro {
    private Long id;
    private String nome;
    private Classes classe;
    private int nivel;
    private boolean ativo;
    private Optional<Companheiro> companheiro;

    public Aventureiro() {
        this.companheiro = Optional.empty();
        this.ativo = true;
    }

    public Aventureiro(Long id, String nome, Classes classe, int nivel) {
        setId(id);
        setNome(nome);
        setClasse(classe);
        setNivel(nivel);
        this.ativo=true;
        this.companheiro = Optional.empty();
    }

    public void setCompanheiro(Companheiro companheiro) {
        this.companheiro = Optional.ofNullable(companheiro);
    }

    public void removerCompanheiro() {
        this.companheiro = Optional.empty();
    }
}
