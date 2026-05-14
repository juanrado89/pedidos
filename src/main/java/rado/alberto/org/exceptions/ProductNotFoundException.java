package rado.alberto.org.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("El producto no existe en la base de datos");
    }
}
