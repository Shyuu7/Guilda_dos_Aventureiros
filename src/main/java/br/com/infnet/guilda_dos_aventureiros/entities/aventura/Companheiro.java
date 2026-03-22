package br.com.infnet.guilda_dos_aventureiros.entities.aventura;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.CompanheiroEspecies;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "companheiros", schema = "aventura",
        indexes = {
            @Index(name = "idx_companheiro_especie", columnList = "especie, lealdade")
        })

@Getter
@Setter
@NoArgsConstructor
public class Companheiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @PrimaryKeyJoinColumn(name = "aventureiro_id", foreignKey = @ForeignKey(name = "fk_companheiro_aventureiro"))
    private Aventureiro aventureiro;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "especie", nullable = false)
    private CompanheiroEspecies especie;

    @Range(min = 0, max = 100, message = "Lealdade deve ser um número inteiro entre 0 e 100")
    @Column(name = "lealdade", nullable = false)
    private int lealdade;

    public Companheiro(String nome, CompanheiroEspecies especie, int lealdade) {
        this.nome = nome;
        this.especie = especie;
        this.lealdade = lealdade;
    }

    void associarAventureiro(Aventureiro aventureiro) {
        this.aventureiro = aventureiro;
        aventureiro.setCompanheiro(this);
    }

    void removerAventureiro() {
        this.aventureiro = null;
    }
}
