package br.com.infnet.guilda_dos_aventureiros.mapper.loja;

import br.com.infnet.guilda_dos_aventureiros.dto.loja.ItemLojaDTO;
import br.com.infnet.guilda_dos_aventureiros.entities.loja.ItemLoja;
import org.springframework.stereotype.Component;

@Component
public class ItemLojaMapper {

    public ItemLojaDTO toDto(ItemLoja itemLoja) {
        if (itemLoja == null) {
            return null;
        }

        return new ItemLojaDTO(
        itemLoja.getNome(),
        itemLoja.getDescricao(),
        itemLoja.getCategoria(),
        itemLoja.getRaridade(),
        itemLoja.getPreco()
        );
    }
}
