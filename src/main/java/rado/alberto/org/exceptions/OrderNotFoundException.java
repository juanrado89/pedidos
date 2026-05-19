package rado.alberto.org.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException() {
        super("La orden no existe");
    }
}
