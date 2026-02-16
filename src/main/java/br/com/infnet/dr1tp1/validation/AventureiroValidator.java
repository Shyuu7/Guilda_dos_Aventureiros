package br.com.infnet.dr1tp1.validation;

import br.com.infnet.dr1tp1.dto.AventureiroDTO;
import br.com.infnet.dr1tp1.enums.Classes;
import br.com.infnet.dr1tp1.exception.InvalidDataException;
import br.com.infnet.dr1tp1.model.Aventureiro;
import br.com.infnet.dr1tp1.records.AventureiroRecord;

import java.util.ArrayList;
import java.util.List;

public class AventureiroValidator {

    public static ValidationResult validate(Aventureiro aventureiro) {
        List<String> errors = new ArrayList<>();

        validateNome(aventureiro.getNome(), errors);
        validateClasse(aventureiro.getClasse(), errors);
        validateNivel(aventureiro.getNivel(), errors);

        return new ValidationResult(errors);
    }

    public static ValidationResult validateForCreation(String nome, Classes classe, int nivel) {
        List<String> errors = new ArrayList<>();

        validateNome(nome, errors);
        validateClasse(classe, errors);
        validateNivel(nivel, errors);

        return new ValidationResult(errors);
    }

    public static ValidationResult validateDTO(AventureiroDTO dto) {
        List<String> errors = new ArrayList<>();

        validateNome(dto.getNome(), errors);
        validateClasse(dto.getClasse(), errors);
        validateNivel(dto.getNivel(), errors);

        return new ValidationResult(errors);
    }

    public static ValidationResult validateRecord(AventureiroRecord record) {
        List<String> errors = new ArrayList<>();

        validateNome(record.nome(), errors);
        validateClasse(record.classe(), errors);
        validateNivel(record.nivel(), errors);

        return new ValidationResult(errors);
    }

    public static void validateDTOAndThrow(AventureiroDTO dto) {
        ValidationResult result = validateDTO(dto);
        if (!result.isValid()) {
            throw new InvalidDataException(result.getErrorMessage());
        }
    }

    public static void validateRecordAndThrow(AventureiroRecord record) {
        ValidationResult result = validateRecord(record);
        if (!result.isValid()) {
            throw new InvalidDataException(result.getErrorMessage());
        }
    }


    public static void validatePagination(int page, int size) {
        List<String> errors = new ArrayList<>();

        if (page < 0) {
            errors.add("Página não pode ser negativa");
        }
        if (size < 1 || size > 50) {
            errors.add("Tamanho deve estar entre 1 e 50");
        }

        if (!errors.isEmpty()) {
            throw new InvalidDataException(String.join(", ", errors));
        }
    }

    public static void validateAndThrow(Aventureiro aventureiro) {
        ValidationResult result = validate(aventureiro);
        if (!result.isValid()) {
            throw new InvalidDataException(result.getErrorMessage());
        }
    }

    public static void validateForCreationAndThrow(String nome, Classes classe, int nivel) {
        ValidationResult result = validateForCreation(nome, classe, nivel);
        if (!result.isValid()) {
            throw new InvalidDataException(result.getErrorMessage());
        }
    }

    private static void validateNome(String nome, List<String> errors) {
        if (nome == null || nome.trim().isEmpty()) {
            errors.add("Nome do aventureiro é obrigatório e não pode ser vazio");
        }
    }

    private static void validateClasse(Classes classe, List<String> errors) {
        if (classe == null) {
            errors.add("Classe deve pertencer ao conjunto permitido");
        }
    }

    private static void validateNivel(int nivel, List<String> errors) {
        if (nivel < 1) {
            errors.add("Nível deve ser maior ou igual a 1");
        }
    }
}
