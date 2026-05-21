package rado.alberto.org.exceptions;

public class InvalidOrderStatusTransitionException extends RuntimeException {
    public InvalidOrderStatusTransitionException() {
        super("Imposible realizar el cambio de estado.");
    }
}
