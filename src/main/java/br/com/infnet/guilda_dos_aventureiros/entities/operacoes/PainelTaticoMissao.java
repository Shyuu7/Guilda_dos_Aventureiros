package br.com.infnet.guilda_dos_aventureiros.entities.operacoes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Subselect("SELECT * FROM operacoes.vw_painel_tatico_missao")
@Synchronize({"operacoes.vw_painel_tatico_missao"})
@Table(name = "vw_painel_tatico_missao", schema = "operacoes")
public class PainelTaticoMissao {
    @Id
    @Column(name = "missao_id")
    private Long missaoId;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "status")
    private String status;

    @Column(name = "nivel_perigo")
    private String nivelPerigo;

    @Column(name = "organizacao_id")
    private Long organizacaoId;

    @Column(name = "total_participantes")
    private Integer totalParticipantes;

    @Column(name = "nivel_medio_equipe")
    private Double nivelMedioEquipe;

    @Column(name = "total_recompensa")
    private Double totalRecompensa;

    @Column(name = "total_mvps")
    private Integer totalMvps;

    @Column(name = "participantes_com_companheiro")
    private Integer participantesComCompanheiro;

    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;

    @Column(name = "indice_prontidao")
    private Double indiceProntidao;
}
