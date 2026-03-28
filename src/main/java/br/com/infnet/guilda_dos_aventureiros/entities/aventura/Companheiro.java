package br.com.infnet.guilda_dos_aventureiros.entities.aventura;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.CompanheiroEspecies;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @PrimaryKeyJoinColumn(name = "aventureiro_id", foreignKey = @ForeignKey(name = "fk_companheiro_aventureiro"))
    @JsonBackReference
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
}
