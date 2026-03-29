package br.com.infnet.guilda_dos_aventureiros.dto.aventura;

import java.time.LocalDateTime;

public record ParticipacaoResumoResponse (
        Long missaoId,
        String missaoTitulo,
        LocalDateTime dataDeRegistro
) {}
