package br.com.infnet.guilda_dos_aventureiros.service.loja;

import br.com.infnet.guilda_dos_aventureiros.entities.loja.ItemLoja;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationRange;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LojaAnalyticsService {

    private final LojaQueryExecutor queryExecutor;

    public Map<String, Long> contarPorCategoria() {
        NativeQuery query = NativeQuery.builder()
                .withAggregation("categorias",
                Aggregation.of(a -> a
                        .terms(t -> t
                                .field("categoria"))))
                .withMaxResults(0)
                .build();

        SearchHits<ItemLoja> searchHits = queryExecutor.executarQueryDeAgregacao(query);
        Map<String, Long> resultado = new HashMap<>();

        if (searchHits.hasAggregations()) {
            ElasticsearchAggregations aggregations = (ElasticsearchAggregations) searchHits.getAggregations();
            aggregations.get("categorias")
                    .aggregation()
                    .getAggregate()
                    .sterms()
                    .buckets()
                    .array()
                    .forEach(bucket -> resultado.put(bucket.key().stringValue(), bucket.docCount()));
        }
        return resultado;
    }

    public Map<String, Long> contarPorRaridade() {
        NativeQuery query = NativeQuery.builder()
                .withAggregation("raridades",
                        Aggregation.of(a -> a
                        .terms(t -> t
                                .field("raridade"))))
                .withMaxResults(0)
                .build();

        SearchHits<ItemLoja> searchHits = queryExecutor.executarQueryDeAgregacao(query);
        Map<String, Long> resultado = new HashMap<>();

        if (searchHits.hasAggregations()) {
            ElasticsearchAggregations aggregations = (ElasticsearchAggregations) searchHits.getAggregations();
            aggregations.get("raridades")
                    .aggregation()
                    .getAggregate()
                    .sterms()
                    .buckets()
                    .array()
                    .forEach(bucket -> resultado.put(bucket.key().stringValue(), bucket.docCount()));
        }
        return resultado;
    }

    public Double calcularPrecoMedio() {
        NativeQuery query = NativeQuery.builder()
                .withAggregation("preco_medio",
                        Aggregation.of(a -> a
                        .avg(avg -> avg
                                .field("preco"))))
                .withMaxResults(0)
                .build();

        SearchHits<ItemLoja> searchHits = queryExecutor.executarQueryDeAgregacao(query);

        if (searchHits.hasAggregations()) {
            ElasticsearchAggregations aggregations = (ElasticsearchAggregations) searchHits.getAggregations();
            return aggregations.get("preco_medio")
                    .aggregation()
                    .getAggregate()
                    .avg()
                    .value();
        }
        return 0.0;
    }

    public Map<String, Long> agruparPorFaixaPreco() {
        NativeQuery query = NativeQuery.builder()
                .withAggregation("faixas",
                        Aggregation.of(a -> a
                        .range(r -> r
                        .field("preco")
                        .ranges(List.of(
                                AggregationRange.of(rr -> rr.to(100.00)),
                                AggregationRange.of(rr -> rr.from(100.00).to(300.00)),
                                AggregationRange.of(rr -> rr.from(300.00).to(700.00)),
                                AggregationRange.of(rr -> rr.from(700.00))
                        ))
                )))
                .withMaxResults(0)
                .build();

        SearchHits<ItemLoja> searchHits = queryExecutor.executarQueryDeAgregacao(query);
        Map<String, Long> resultado = new LinkedHashMap<>();

        if (searchHits.hasAggregations()) {
            ElasticsearchAggregations aggregations = (ElasticsearchAggregations) searchHits.getAggregations();
            var buckets = aggregations.get("faixas")
                    .aggregation()
                    .getAggregate()
                    .range()
                    .buckets()
                    .array();
            resultado.put("Menor que 100", buckets.get(0).docCount());
            resultado.put("De 100 a 300", buckets.get(1).docCount());
            resultado.put("De 300 a 700", buckets.get(2).docCount());
            resultado.put("Maior ou igual a 700", buckets.get(3).docCount());
        }
        return resultado;
    }
}
