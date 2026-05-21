package rado.alberto.org.exceptions;

public class InvalidPaymentStatusTransitionException extends RuntimeException {
    public InvalidPaymentStatusTransitionException() {
        super("Imposible realizar el cambio de estado.");
    }
}
