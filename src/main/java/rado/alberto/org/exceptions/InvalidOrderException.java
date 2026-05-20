package rado.alberto.org.exceptions;

public class InvalidOrderException extends RuntimeException {
    public InvalidOrderException() {
        super("El formato de la orden no es valido.");
    }
}
