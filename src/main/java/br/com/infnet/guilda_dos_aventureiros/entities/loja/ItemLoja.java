package br.com.infnet.guilda_dos_aventureiros.entities.loja;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Document(indexName = "guilda_loja")
public class ItemLoja {
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "nome")
    private String nome;

    @Field(type = FieldType.Text, name = "descricao")
    private String descricao;

    @Field(type = FieldType.Keyword, name = "categoria")
    private String categoria;

    @Field(type = FieldType.Keyword, name = "raridade")
    private String raridade;

    @Field(type = FieldType.Double, name = "preco")
    private Double preco;
}
