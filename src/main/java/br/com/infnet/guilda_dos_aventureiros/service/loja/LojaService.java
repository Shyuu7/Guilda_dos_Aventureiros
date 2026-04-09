package br.com.infnet.guilda_dos_aventureiros.service.loja;

import br.com.infnet.guilda_dos_aventureiros.dto.loja.ItemLojaDTO;
import br.com.infnet.guilda_dos_aventureiros.entities.loja.ItemLoja;
import br.com.infnet.guilda_dos_aventureiros.mapper.loja.ItemLojaMapper;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LojaService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final ItemLojaMapper itemLojaMapper;

    public List<ItemLojaDTO> buscarPorNome(String termo) {
        Query query = Query.of(q -> q
                .match(m -> m
                        .field("nome")
                        .query(termo)
                )
        );
        return executarQuery(query);
    }

    public List<ItemLojaDTO> buscarPorDescricao(String termo) {
        Query query = Query.of(q -> q
                .match(m -> m
                        .field("descricao")
                        .query(termo)
                        .operator(Operator.And)
                )
        );
        return executarQuery(query);
    }

    public List<ItemLojaDTO> buscarPorFraseExata(String termo) {
        Query query = Query.of(q -> q
                .matchPhrase(mp -> mp
                        .field("descricao")
                        .query(termo)
                )
        );
        return executarQuery(query);
    }

    public List<ItemLojaDTO> buscarPorNomeFuzzy(String termo) {
        Query query = Query.of(q -> q
                .fuzzy(f -> f
                        .field("nome")
                        .value(termo)
                        .fuzziness("AUTO")
                )
        );
        return executarQuery(query);
    }

    public List<ItemLojaDTO> buscarEmMultiplosCampos(String termo) {
        Query query = Query.of(q -> q
                .multiMatch(mm -> mm
                        .fields("nome", "descricao")
                        .query(termo)
                )
        );
        return executarQuery(query);
    }

    public List<ItemLojaDTO> buscarPorDescricaoFiltrarCategoria(String termo, String categoria) {
        Query query = Query.of(q -> q
                .bool(b -> b
                        .must(m -> m
                                .match(mm -> mm
                                        .field("descricao")
                                        .query(termo)
                                )
                        )
                        .filter(f -> f
                                .term(t -> t
                                        .field("categoria")
                                        .value(categoria)
                                )
                        )
                )
        );
        return executarQuery(query);
    }

    public List<ItemLojaDTO> buscarPorFaixaDePreco(Double min, Double max) {
        Query priceRangeQuery = Query.of(q -> q
                .range(r -> r
                        .number(n -> n
                                .field("preco")
                                .lte(max)
                                .gte(min)
                        )
                )
        );
        return executarQuery(priceRangeQuery);
    }

    public List<ItemLojaDTO> buscaCombinada(String categoria, String raridade, Double min, Double max) {
        BoolQuery.Builder boolQuery = new BoolQuery.Builder();

        if (categoria != null && !categoria.isEmpty()) {
            boolQuery.filter(f -> f
                    .term(t -> t
                            .field("categoria")
                            .value(categoria)));
        }

        if (raridade != null && !raridade.isEmpty()) {
            boolQuery.filter(f -> f
                    .term(t -> t
                            .field("raridade")
                            .value(raridade)));
        }

        if (min != null || max != null) {
            boolQuery.filter(f -> f
                    .range(r -> r
                            .number(n -> {
                                n.field("preco");
                                if (min != null) {
                                    n.gte(min);
                                }
                                if (max != null) {
                                    n.lte(max);
                                }
                                return n;
                            })
                    ));
        }
        Query query = Query.of(q -> q
                .bool(boolQuery.build()));

        return executarQuery(query);
    }

    private List<ItemLojaDTO> executarQuery(Query query) {
        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(query)
                .build();

        SearchHits<ItemLoja> searchHits = elasticsearchOperations.search(nativeQuery, ItemLoja.class);

        return searchHits.stream()
                .map(SearchHit::getContent)
                .map(itemLojaMapper::toDto)
                .toList();
    }
}
