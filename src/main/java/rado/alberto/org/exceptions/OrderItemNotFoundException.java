package rado.alberto.org.exceptions;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException() {
        super("No se encuentra el producto en su orden.");
    }
}
