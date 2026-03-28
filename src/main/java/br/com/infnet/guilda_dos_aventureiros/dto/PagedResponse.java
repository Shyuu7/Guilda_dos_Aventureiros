package br.com.infnet.guilda_dos_aventureiros.dto;

import java.util.List;

public record PagedResponse<T>(
        int page,
        int size,
        long total,
        int totalPages,
        List<T> content
) {
}
