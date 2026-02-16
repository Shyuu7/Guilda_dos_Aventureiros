package br.com.infnet.dr1tp1.validation;

import br.com.infnet.dr1tp1.enums.Especies;
import br.com.infnet.dr1tp1.exception.InvalidDataException;
import br.com.infnet.dr1tp1.model.Companheiro;

import java.util.ArrayList;
import java.util.List;

public class CompanheiroValidator {

    public static ValidationResult validate(Companheiro companheiro) {
        List<String> errors = new ArrayList<>();

        validateNome(companheiro.getNome(), errors);
        validateEspecie(companheiro.getEspecie(), errors);
        validateLealdade(companheiro.getLealdade(), errors);

        return new ValidationResult(errors);
    }

    public static ValidationResult validateForCreation(String nome, Especies especie, int lealdade) {
        List<String> errors = new ArrayList<>();

        validateNome(nome, errors);
        validateEspecie(especie, errors);
        validateLealdade(lealdade, errors);

        return new ValidationResult(errors);
    }

    public static void validateAndThrow(Companheiro companheiro){
        ValidationResult result = validate(companheiro);
        if (!result.isValid()) {
            throw new InvalidDataException(result.getErrorMessage());
        }
    }

    public static void validateForCreationAndThrow(String nome, Especies especie, int lealdade) {
        ValidationResult result = validateForCreation(nome, especie, lealdade);
        if (!result.isValid()) {
            throw new InvalidDataException(result.getErrorMessage());
        }
    }

    private static void validateNome(String nome, List<String> errors) {
        if (nome == null || nome.trim().isEmpty()) {
            errors.add("Nome do companheiro é obrigatório e não pode ser vazio");
        }
    }

    private static void validateEspecie(Especies especie, List<String> errors) {
        if (especie == null) {
            errors.add("Espécie deve pertencer ao conjunto permitido");
        }
    }

    private static void validateLealdade(int lealdade, List<String> errors) {
        if (lealdade < 0 || lealdade > 100) {
            errors.add("Lealdade deve estar entre 0 e 100");
        }
    }
}
