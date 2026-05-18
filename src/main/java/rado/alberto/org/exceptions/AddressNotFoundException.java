package rado.alberto.org.exceptions;

public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException() {
        super("La dirección no existe en la base de datos");
    }
}
