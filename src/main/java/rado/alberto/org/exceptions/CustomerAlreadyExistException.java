package rado.alberto.org.exceptions;

public class CustomerAlreadyExistException extends RuntimeException{
    public  CustomerAlreadyExistException(){
        super ("Un cliente ya se ha registrado con este e-mail");
    }
}
