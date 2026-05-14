package rado.alberto.org.exceptions;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException() {
        super("El cliente no existe");
    }
}
