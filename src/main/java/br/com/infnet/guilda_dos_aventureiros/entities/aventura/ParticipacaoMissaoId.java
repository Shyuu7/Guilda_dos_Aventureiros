package br.com.infnet.guilda_dos_aventureiros.entities.aventura;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipacaoMissaoId implements Serializable {
    private Long missao;
    private Long aventureiro;
}
