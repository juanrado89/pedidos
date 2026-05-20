package rado.alberto.org.exceptions;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Las credenciales de acceso no son validas.");
    }
}
