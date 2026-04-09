package br.com.infnet.guilda_dos_aventureiros.service.loja;

import br.com.infnet.guilda_dos_aventureiros.dto.loja.ItemLojaDTO;
import br.com.infnet.guilda_dos_aventureiros.entities.loja.ItemLoja;
import br.com.infnet.guilda_dos_aventureiros.mapper.loja.ItemLojaMapper;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.RangeBucket;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
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
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LojaService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final ItemLojaMapper itemLojaMapper;

    //-------Métodos de busca-----------
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

    //-------Consultas combinadas-----------
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


    //----Agregações e métricas-----------
    public Map<String, Long> getContagemPorCategoria() {
        String nomeAgregacao = "contagem_por_categoria";
        Aggregation agregacao = Aggregation.of(a -> a
                .terms(t -> t
                        .field("categoria"))
        );

        Aggregate aggregate = executarAgregacao(nomeAgregacao, agregacao);

        return aggregate.sterms().buckets().array()
                .stream()
                .collect(Collectors.toMap(
                        b -> b
                                .key()
                                .stringValue(),
                        StringTermsBucket::docCount
                ));
    }

    public Map<String, Long> getContagemPorRaridade() {
        String nomeAgregacao = "contagem_por_raridade";
        Aggregation agregacao = Aggregation.of(a -> a
                .terms(t -> t
                        .field("raridade"))
        );

        Aggregate aggregate = executarAgregacao(nomeAgregacao, agregacao);

        return aggregate.sterms().buckets().array()
                .stream()
                .collect(Collectors.toMap(
                        b -> b
                                .key()
                                .stringValue(),
                        StringTermsBucket::docCount
                ));
    }

    public Double getPrecoMedio() {
        String nomeAgregacao = "preco_medio";
        Aggregation agregacao = Aggregation.of(a -> a
                .avg(avg -> avg
                        .field("preco"))
        );
        Aggregate aggregate = executarAgregacao(nomeAgregacao, agregacao);
        return aggregate.avg().value();
    }

    public Map<String, Long> getContagemPorFaixaDePreco() {
        String nomeAgregacao = "faixas_de_preco";
        Aggregation agregacao = Aggregation.of(a -> a
                .range(ra -> ra
                        .field("preco")
                        //não sei o que mais colocar aqui
                )
        );

        Aggregate aggregate = executarAgregacao(nomeAgregacao, agregacao);

        return aggregate.range().buckets().array()
                .stream()
                .collect(Collectors.toMap(
                        RangeBucket::key,
                        RangeBucket::docCount
                ));
    }

    //-------Métodos auxiliares-----------
    private Aggregate executarAgregacao(String nomeAgregacao, Aggregation agregacao) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .matchAll(m -> m))
                .withAggregation(nomeAgregacao, agregacao)
                .withMaxResults(0)
                .build();

        SearchHits<ItemLoja> searchHits = elasticsearchOperations.search(query, ItemLoja.class);
        //Como que eu trabalho essa descompactação????????
        return null;
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
