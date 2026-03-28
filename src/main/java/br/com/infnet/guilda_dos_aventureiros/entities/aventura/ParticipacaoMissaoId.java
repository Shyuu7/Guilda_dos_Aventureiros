package br.com.infnet.guilda_dos_aventureiros.entities.aventura;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipacaoMissaoId implements Serializable {
    private Long missao;
    private Long aventureiro;
}
