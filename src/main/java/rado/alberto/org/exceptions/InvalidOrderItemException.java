package rado.alberto.org.exceptions;

public class InvalidOrderItemException extends RuntimeException {
    public InvalidOrderItemException() {
        super("El formato del producto no es valido.");
    }
}
