package br.com.infnet.guilda_dos_aventureiros.service.loja;

import br.com.infnet.guilda_dos_aventureiros.entities.loja.ItemLoja;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO: implementar DTOs para evitar expor a entidade diretamente e permitir transformações futuras sem quebrar a API

@Service
@RequiredArgsConstructor
public class LojaService {

    private final ElasticsearchOperations elasticsearchOperations;

    public List<ItemLoja> buscarPorNome(String termo) {
        Query query = Query.of(q -> q
                .match(m -> m
                        .field("nome")
                        .query(termo)
                )
        );
        return executarQuery(query);
    }

    public List<ItemLoja> buscarPorDescricao(String termo) {
        Query query = Query.of(q -> q
                .match(m -> m
                        .field("descricao")
                        .query(termo)
                )
        );
        return executarQuery(query);
    }

    public List<ItemLoja> buscarPorFraseExata(String termo) {
        Query query = Query.of(q -> q
                .matchPhrase(mp -> mp
                        .field("descricao")
                        .query(termo)
                )
        );
        return executarQuery(query);
    }

    public List<ItemLoja> buscarPorNomeFuzzy(String termo) {
        Query query = Query.of(q -> q
                .fuzzy(f -> f
                        .field("nome")
                        .value(termo)
                        .fuzziness("AUTO")
                )
        );
        return executarQuery(query);
    }

    public List<ItemLoja> buscarEmMultiplosCampos(String termo) {
        Query query = Query.of(q -> q
                .multiMatch(mm -> mm
                        .fields("nome", "descricao")
                        .query(termo)
                )
        );
        return executarQuery(query);
    }

    private List<ItemLoja> executarQuery(Query query) {
        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(query)
                .build();

        SearchHits<ItemLoja> searchHits = elasticsearchOperations.search(nativeQuery, ItemLoja.class);
        return searchHits.stream()
                .map(SearchHit::getContent)
                .toList();
    }
}
