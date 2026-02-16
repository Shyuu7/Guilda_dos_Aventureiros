package br.com.infnet.dr1tp1.dto;

import br.com.infnet.dr1tp1.enums.Especies;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanheiroDTO {
    private String nome;
    private Especies especie;
    private int lealdade;
}
