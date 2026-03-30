package br.com.infnet.guilda_dos_aventureiros.dto.relatorios;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonPropertyOrder({"titulo", "status", "nivelPerigo", "dataCriacao", "quantidadeParticipantes", "totalRecompensas"})
public interface RelatorioMissaoProjection {
    String getTitulo();
    String getStatus();
    String getNivelPerigo();
    Long getQuantidadeParticipantes();
    BigDecimal getTotalRecompensas();
    LocalDateTime getDataCriacao();
}
