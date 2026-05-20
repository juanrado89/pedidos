package rado.alberto.org.exceptions;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException() {
        super("No se encontró el pago.");
    }
}
