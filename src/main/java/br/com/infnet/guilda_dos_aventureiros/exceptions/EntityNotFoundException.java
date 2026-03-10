package br.com.infnet.guilda_dos_aventureiros.exceptions;

public class EntityNotFoundException extends IllegalArgumentException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
