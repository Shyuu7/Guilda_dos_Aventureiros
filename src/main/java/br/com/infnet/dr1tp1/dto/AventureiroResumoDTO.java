package br.com.infnet.dr1tp1.dto;

import br.com.infnet.dr1tp1.enums.Classes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AventureiroResumoDTO {
    private Long id;
    private String nome;
    private Classes classe;
    private int nivel;
    private boolean ativo;
}
