package br.com.infnet.dr1tp1.controller;

import br.com.infnet.dr1tp1.dto.AventureiroDTO;
import br.com.infnet.dr1tp1.dto.AventureiroResumoDTO;
import br.com.infnet.dr1tp1.enums.Classes;
import br.com.infnet.dr1tp1.records.AventureiroRecord;
import br.com.infnet.dr1tp1.service.AventureiroService;
import br.com.infnet.dr1tp1.validation.AventureiroValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aventureiros")
@Validated
public class AventureiroController {

    private final AventureiroService aventureiroService;

    public AventureiroController(AventureiroService aventureiroService) {
        this.aventureiroService = aventureiroService;
    }

    //=====================
    // CRIAR AVENTUREIRO
    // [POST]
    //=====================
    @PostMapping
    public ResponseEntity<AventureiroDTO> registrarAventureiro(@RequestBody AventureiroRecord request) {
        AventureiroValidator.validateRecordAndThrow(request);

        AventureiroDTO aventureiro = aventureiroService.registrarAventureiro(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(aventureiro);
    }

    //=====================
    // LISTAR AVENTUREIROS
    // [GET] com filtros e paginação
    //=====================
    @GetMapping
    public ResponseEntity<List<AventureiroResumoDTO>> listarAventureiros(
            @RequestParam(required = false) Classes classe,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Integer nivelMinimo,
            @RequestHeader(value = "X-Page", defaultValue = "0") int page,
            @RequestHeader(value = "X-Size", defaultValue = "10") int size) {

        AventureiroValidator.validatePagination(page, size);

        List<AventureiroResumoDTO> aventureiros = aventureiroService.listarComFiltros(classe, ativo, nivelMinimo, page, size);
        long totalCount = aventureiroService.contarComFiltros(classe, ativo, nivelMinimo);
        int totalPages = (int) Math.ceil((double) totalCount / size);

        return ResponseEntity.status(HttpStatus.OK)
                .header("X-Page", String.valueOf(page))
                .header("X-Size", String.valueOf(size))
                .header("X-Total-Count", String.valueOf(totalCount))
                .header("X-Total-Pages", String.valueOf(totalPages))
                .body(aventureiros);
    }

    //=====================
    // CONSULTAR POR ID
    // [GET]
    // /aventureiros/1
    //=====================
    @GetMapping("/{id}")
    public ResponseEntity<?> consultarPorId(@Positive @PathVariable Long id) {
        AventureiroDTO aventureiro = aventureiroService.consultarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(aventureiro);
    }

    //=====================
    // ATUALIZAR AVENTUREIRO
    // [PUT]
    // /aventureiros/1
    //=====================
    @PutMapping("/{id}")
    public ResponseEntity<AventureiroDTO> atualizarAventureiro(
            @Positive @PathVariable Long id,
            @RequestBody AventureiroDTO request) {

        AventureiroValidator.validateDTOAndThrow(request);

        AventureiroDTO aventureiro = aventureiroService.atualizarAventureiro(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(aventureiro);
    }

    //=====================
    // ENCERRAR VÍNCULO
    // [PUT]
    // /aventureiros/1/encerrar
    //=====================
    @PutMapping("/{id}/encerrar")
    public ResponseEntity<AventureiroDTO> encerrarVinculo(@Positive @PathVariable Long id) {
        AventureiroDTO aventureiro = aventureiroService.alterarStatusAtivo(id, false);
        return ResponseEntity.status(HttpStatus.OK).body(aventureiro);
    }

    //=====================
    // RECRUTAR NOVAMENTE
    // [PUT]
    // /aventureiros/1/recrutar
    //=====================
    @PutMapping("/{id}/recrutar")
    public ResponseEntity<AventureiroDTO> recrutarNovamente(@Positive @PathVariable Long id) {
        AventureiroDTO aventureiro = aventureiroService.alterarStatusAtivo(id, true);
        return ResponseEntity.status(HttpStatus.OK).body(aventureiro);
    }
}
