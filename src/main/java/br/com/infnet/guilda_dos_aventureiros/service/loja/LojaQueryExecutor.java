package br.com.infnet.guilda_dos_aventureiros.service.loja;

import br.com.infnet.guilda_dos_aventureiros.dto.loja.ItemLojaDTO;
import br.com.infnet.guilda_dos_aventureiros.entities.loja.ItemLoja;
import br.com.infnet.guilda_dos_aventureiros.mapper.loja.ItemLojaMapper;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LojaQueryExecutor {

    private final ElasticsearchOperations elasticsearchOperations;
    private final ItemLojaMapper itemLojaMapper;

    public List<ItemLojaDTO> executarQueryDeBusca(Query query) {
        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(query)
                .build();

        SearchHits<ItemLoja> searchHits = elasticsearchOperations.search(nativeQuery, ItemLoja.class);

        return searchHits.stream()
                .map(SearchHit::getContent)
                .map(itemLojaMapper::toDto)
                .toList();
    }

    public SearchHits<ItemLoja> executarQueryDeAgregacao(NativeQuery query) {
        return elasticsearchOperations.search(query, ItemLoja.class);
    }
}
