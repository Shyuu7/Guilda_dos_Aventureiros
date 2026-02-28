package br.com.infnet.dr1tp1.exceptions;

public class EntityNotFoundException extends IllegalArgumentException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
