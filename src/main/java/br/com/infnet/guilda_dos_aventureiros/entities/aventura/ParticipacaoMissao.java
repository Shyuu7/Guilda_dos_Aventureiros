package br.com.infnet.guilda_dos_aventureiros.entities.aventura;

import br.com.infnet.guilda_dos_aventureiros.enums.aventura.PapelMissao;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "participacoes_missoes", schema = "aventura")
@IdClass(ParticipacaoMissaoId.class)
public class ParticipacaoMissao {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missao_id", nullable = false)
    private Missao missao;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private Aventureiro aventureiro;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel_missao", nullable = false)
    private PapelMissao papelMissao;

    @PositiveOrZero
    @Column(name = "recompensa_ouro")
    private BigDecimal recompensaEmOuro;

    @Column(nullable = false)
    private boolean destaque;

    @CreationTimestamp
    @Column(name = "data_registro", nullable = false, updatable = false)
    private LocalDateTime dataDeRegistro;
}
