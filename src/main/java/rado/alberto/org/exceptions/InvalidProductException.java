package rado.alberto.org.exceptions;

public class InvalidProductException extends RuntimeException {
    public InvalidProductException() {
        super("Formato de producto invalido");
    }
}
