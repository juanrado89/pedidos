package rado.alberto.org.exceptions;

public class InvalidAddressException extends RuntimeException {

    public InvalidAddressException() {
        super("La dirección no esta bien formada");
    }
}
