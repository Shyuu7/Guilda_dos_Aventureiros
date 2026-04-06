package br.com.infnet.guilda_dos_aventureiros.dto.operacoes;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PainelTaticoMissaoDTO {
    private Long missaoId;
    private String titulo;
    private String status;
    private String nivelPerigo;
    private Long organizacaoId;
    private Integer totalParticipantes;
    private Double nivelMedioEquipe;
    private Double totalRecompensa;
    private Integer totalMvps;
    private Integer participantesComCompanheiro;
    private LocalDateTime ultimaAtualizacao;
    private Double indiceProntidao;
}
