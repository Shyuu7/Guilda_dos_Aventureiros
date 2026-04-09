package br.com.infnet.guilda_dos_aventureiros.controllers.loja;

import br.com.infnet.guilda_dos_aventureiros.dto.loja.ItemLojaDTO;
import br.com.infnet.guilda_dos_aventureiros.service.loja.LojaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class LojaController {

    private final LojaService lojaService;

    @GetMapping("/busca/nome")
    public ResponseEntity<List<ItemLojaDTO>> buscarPorNome(@RequestParam String termo) {
        return ResponseEntity.ok(lojaService.buscarPorNome(termo));
    }

    @GetMapping("/busca/descricao")
    public ResponseEntity<List<ItemLojaDTO>> buscarPorDescricao(@RequestParam String termo) {
        return ResponseEntity.ok(lojaService.buscarPorDescricao(termo));
    }

    @GetMapping("/busca/frase")
    public ResponseEntity<List<ItemLojaDTO>> buscarPorFrase(@RequestParam String termo) {
        return ResponseEntity.ok(lojaService.buscarPorFraseExata(termo));
    }

    @GetMapping("/busca/fuzzy")
    public ResponseEntity<List<ItemLojaDTO>> buscarPorNomeFuzzy(@RequestParam String termo) {
        return ResponseEntity.ok(lojaService.buscarPorNomeFuzzy(termo));
    }

    @GetMapping("/busca/multicampos")
    public ResponseEntity<List<ItemLojaDTO>> buscarEmMultiplosCampos(@RequestParam String termo) {
        return ResponseEntity.ok(lojaService.buscarEmMultiplosCampos(termo));
    }

    @GetMapping("/busca/com-filtro")
    public ResponseEntity<List<ItemLojaDTO>> buscarComFiltro(
            @RequestParam String termo,
            @RequestParam String categoria
    ) {
        return ResponseEntity.ok(lojaService.buscarPorDescricaoFiltrarCategoria(termo, categoria));
    }

    @GetMapping("/busca/faixa-preco")
    public ResponseEntity<List<ItemLojaDTO>> buscarPorFaixaDePreco(
            @RequestParam Double min,
            @RequestParam Double max
    ) {
        return ResponseEntity.ok(lojaService.buscarPorFaixaDePreco(min, max));
    }

    @GetMapping("/combinada")
    public ResponseEntity<List<ItemLojaDTO>> buscaCombinada(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String raridade,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max
    ) {
        return ResponseEntity.ok(lojaService.buscaCombinada(categoria, raridade, min, max));
    }

    // --- Endpoints de Agregação ---
    @GetMapping("/agregacoes/por-categoria")
    public ResponseEntity<Map<String, Long>> getContagemPorCategoria() {
        return ResponseEntity.ok(lojaService.contarPorCategoria());
    }

    @GetMapping("/agregacoes/por-raridade")
    public ResponseEntity<Map<String, Long>> getContagemPorRaridade() {
        return ResponseEntity.ok(lojaService.contarPorRaridade());
    }

    @GetMapping("/agregacoes/preco-medio")
    public ResponseEntity<Double> getPrecoMedio() {
        return ResponseEntity.ok(lojaService.calcularPrecoMedio());
    }

    @GetMapping("/agregacoes/faixas-preco")
    public ResponseEntity<Map<String, Long>> getContagemPorFaixaDePreco() {
        return ResponseEntity.ok(lojaService.agruparPorFaixaPreco());
    }
}
