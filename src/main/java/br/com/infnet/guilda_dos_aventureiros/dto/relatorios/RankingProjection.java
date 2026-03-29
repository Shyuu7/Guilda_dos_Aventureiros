package br.com.infnet.guilda_dos_aventureiros.dto.relatorios;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"nomeAventureiro", "totalParticipacoes", "totalRecompensas", "totalDestaques"})
public interface RankingProjection {
    String getNomeAventureiro();
    Long getTotalParticipacoes();
    Double getTotalRecompensas();
    Long getTotalDestaques();
}
