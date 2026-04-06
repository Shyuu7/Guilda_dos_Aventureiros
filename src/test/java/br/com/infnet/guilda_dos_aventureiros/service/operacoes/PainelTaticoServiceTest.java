package br.com.infnet.guilda_dos_aventureiros.service.operacoes;

import br.com.infnet.guilda_dos_aventureiros.entities.operacoes.PainelTaticoMissao;
import br.com.infnet.guilda_dos_aventureiros.repositories.operacoes.PainelTaticoMissaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PainelTaticoServiceTest {

    @Mock
    private PainelTaticoMissaoRepository painelTaticoMissaoRepository;

    @InjectMocks
    private PainelTaticoService painelTaticoService;

    private PainelTaticoMissao missaoFalsa;
    private PainelTaticoMissao missaoFalsa2;

    @BeforeEach
    void setUp() {
        missaoFalsa = new PainelTaticoMissao();
        missaoFalsa.setMissaoId(1L);
        missaoFalsa.setTitulo("Primeira Missão de Teste");
        missaoFalsa.setIndiceProntidao(99.5);
        missaoFalsa.setUltimaAtualizacao(LocalDateTime.of(2026,3,31,12,0));

        missaoFalsa2 = new PainelTaticoMissao();
        missaoFalsa2.setMissaoId(2L);
        missaoFalsa2.setTitulo("Segunda Missão de Teste");
        missaoFalsa2.setIndiceProntidao(85.0);
        missaoFalsa2.setUltimaAtualizacao(LocalDateTime.of(2026,3,30,12,0));
    }

    @Test
    @DisplayName("Deve retornar a lista de missões do repositório com sucesso, ordenada por índice de prontidão")
    void findTopMissoesUltimos15Dias_deveRetornarListaDeMissoes() {
        List<PainelTaticoMissao> listaDeMissoesFalsas = List.of(missaoFalsa, missaoFalsa2);

        when(painelTaticoMissaoRepository.findTop10ByUltimaAtualizacaoBetweenOrderByIndiceProntidaoDesc(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(listaDeMissoesFalsas);

       List<PainelTaticoMissao> resultado = painelTaticoService.findTopMissoesUltimos15Dias();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        PainelTaticoMissao primeiraMissao = resultado.getFirst();

        assertEquals(1L, primeiraMissao.getMissaoId());
        assertEquals("Primeira Missão de Teste", primeiraMissao.getTitulo());
        assertEquals(99.5, primeiraMissao.getIndiceProntidao());

        PainelTaticoMissao segundaMissao = resultado.get(1);
        assertEquals(2L, segundaMissao.getMissaoId());
        assertEquals("Segunda Missão de Teste", segundaMissao.getTitulo());
        assertEquals(85.0, segundaMissao.getIndiceProntidao());

        verify(painelTaticoMissaoRepository).findTop10ByUltimaAtualizacaoBetweenOrderByIndiceProntidaoDesc(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    @DisplayName("Deve chamar o repositório com o intervalo de datas correto (últimos 15 dias)")
    void findTopMissoesUltimos15Dias_deveCalcularDatasCorretamente() {
        ArgumentCaptor<LocalDateTime> dataInicioCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        ArgumentCaptor<LocalDateTime> dataFimCaptor = ArgumentCaptor.forClass(LocalDateTime.class);

        when(painelTaticoMissaoRepository.findTop10ByUltimaAtualizacaoBetweenOrderByIndiceProntidaoDesc(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(missaoFalsa, missaoFalsa2));

        painelTaticoService.findTopMissoesUltimos15Dias();

        verify(painelTaticoMissaoRepository).findTop10ByUltimaAtualizacaoBetweenOrderByIndiceProntidaoDesc(
                dataInicioCaptor.capture(),
                dataFimCaptor.capture()
        );

        LocalDateTime dataInicioPassada = dataInicioCaptor.getValue();
        LocalDateTime dataFimPassada = dataFimCaptor.getValue();
        LocalDateTime agora = LocalDateTime.now();

        assertNotNull(dataInicioPassada);
        assertNotNull(dataFimPassada);

        long diffFim = ChronoUnit.SECONDS.between(dataFimPassada, agora);
        assertEquals(0, diffFim, "A data de fim deve ser o momento atual.");

        long diffInicio = ChronoUnit.DAYS.between(dataInicioPassada, dataFimPassada);
        assertEquals(15, diffInicio, "O intervalo entre a data de início e fim deve ser de 15 dias.");
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando o repositório não encontrar missões")
    void findTopMissoesUltimos15Dias_deveRetornarListaVazia() {
        when(painelTaticoMissaoRepository.findTop10ByUltimaAtualizacaoBetweenOrderByIndiceProntidaoDesc(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.emptyList());
        List<PainelTaticoMissao> resultado = painelTaticoService.findTopMissoesUltimos15Dias();
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }
}
