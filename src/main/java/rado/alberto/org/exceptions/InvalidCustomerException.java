package rado.alberto.org.exceptions;

public class InvalidCustomerException extends RuntimeException {
    public InvalidCustomerException() {
        super("Formato de cliente no valido");
    }
}
