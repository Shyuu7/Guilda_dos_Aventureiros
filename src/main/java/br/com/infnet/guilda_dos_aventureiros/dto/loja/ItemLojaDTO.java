package br.com.infnet.guilda_dos_aventureiros.dto.loja;

public record ItemLojaDTO(
        String nome,
        String descricao,
        String categoria,
        String raridade,
        Double preco
) {
}
