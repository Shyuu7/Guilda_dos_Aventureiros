package br.com.infnet.dr1tp1.exception;

public class AventureiroNotFoundException extends AventureiroException {
    public AventureiroNotFoundException(Long id) {
        super("Aventureiro com ID " + id + " não encontrado.");
    }
}
