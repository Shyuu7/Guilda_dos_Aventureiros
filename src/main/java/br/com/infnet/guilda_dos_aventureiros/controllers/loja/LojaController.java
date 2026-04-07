package br.com.infnet.guilda_dos_aventureiros.controllers.loja;

import br.com.infnet.guilda_dos_aventureiros.entities.loja.ItemLoja;
import br.com.infnet.guilda_dos_aventureiros.service.loja.LojaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos/busca")
@RequiredArgsConstructor
public class LojaController {

    private final LojaService lojaService;

    @GetMapping("/nome")
    public ResponseEntity<List<ItemLoja>> buscarPorNome(@RequestParam String termo) {
        return ResponseEntity.ok(lojaService.buscarPorNome(termo));
    }

    @GetMapping("/descricao")
    public ResponseEntity<List<ItemLoja>> buscarPorDescricao(@RequestParam String termo) {
        return ResponseEntity.ok(lojaService.buscarPorDescricao(termo));
    }

    @GetMapping("/frase")
    public ResponseEntity<List<ItemLoja>> buscarPorFrase(@RequestParam String termo) {
        return ResponseEntity.ok(lojaService.buscarPorFraseExata(termo));
    }

    @GetMapping("/fuzzy")
    public ResponseEntity<List<ItemLoja>> buscarPorNomeFuzzy(@RequestParam String termo) {
        return ResponseEntity.ok(lojaService.buscarPorNomeFuzzy(termo));
    }

    @GetMapping("/multicampos")
    public ResponseEntity<List<ItemLoja>> buscarEmMultiplosCampos(@RequestParam String termo) {
        return ResponseEntity.ok(lojaService.buscarEmMultiplosCampos(termo));
    }
}
