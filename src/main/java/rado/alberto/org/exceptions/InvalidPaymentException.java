package rado.alberto.org.exceptions;

public class InvalidPaymentException extends RuntimeException {
    public InvalidPaymentException() {
        super("El formato del pago no es valido.");
    }
}
