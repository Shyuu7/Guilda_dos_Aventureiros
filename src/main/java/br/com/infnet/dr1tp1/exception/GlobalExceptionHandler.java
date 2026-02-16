package br.com.infnet.dr1tp1.exception;

import br.com.infnet.dr1tp1.payload.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidData(InvalidDataException e) {
        List<String> detalhes = Arrays.asList(e.getMessage().split("; "));
        ErrorResponse error = new ErrorResponse("Solicitação inválida", detalhes);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(AventureiroNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(AventureiroNotFoundException e) {
        ErrorResponse error = new ErrorResponse("Recurso não encontrado", Collections.singletonList(e.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException e) {
        ErrorResponse error = new ErrorResponse("Erro de validação", Collections.singletonList(e.getMessage()));
        return ResponseEntity.badRequest().body(error);
    }
}
