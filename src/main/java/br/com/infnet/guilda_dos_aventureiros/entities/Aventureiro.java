package br.com.infnet.guilda_dos_aventureiros.entities;

import br.com.infnet.guilda_dos_aventureiros.entities.audit.User;
import br.com.infnet.guilda_dos_aventureiros.enums.AventureiroClasses;
import br.com.infnet.guilda_dos_aventureiros.entities.audit.Organization;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aventureiros", schema = "aventura", check = {
            @CheckConstraint(name = "ck_aventureiro_nivel", constraint = "nivel >= 1")
        },
        indexes = {
            @Index(name = "idx_aventureiros_org_classe_nivel", columnList = "organizacao_id, classe, nivel")
        })

public class Aventureiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "nome", nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "classe", nullable = false)
    private AventureiroClasses classe;

    @Positive
    @Column(name = "nivel")
    private int nivel;

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true) //Não existe companheiro sem aventureiro
    @JoinColumn(name = "companheiro_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_aventureiro_companheiro"))
    private Companheiro companheiro;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organizacao_id", nullable = false, foreignKey = @ForeignKey(name = "fk_aventureiros_org"))
    private Organization organizacao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false, foreignKey = @ForeignKey(name = "fk_aventureiros_usuario"))
    @NotNull
    private User usuario;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @CreationTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    public Aventureiro(String nome, AventureiroClasses classe, int nivel) {
        this.nome = nome;
        this.classe = classe;
        this.nivel = nivel;
    }

    public void recrutar() {
        this.ativo = true;
    }

    public void encerrarVinculo() {
        this.ativo = false;
    }
}
