package br.com.infnet.guilda_dos_aventureiros.dto.relatorios;

import java.time.LocalDateTime;

public record RelatorioFiltroRequest(
        LocalDateTime inicio,
        LocalDateTime termino
) {
}
