package rado.alberto.org.exceptions;

public class ProductAlreadyExistException extends RuntimeException {

    public ProductAlreadyExistException() {
        super("El producto ya existe en la base de datos");
    }
}
