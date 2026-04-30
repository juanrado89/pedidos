package rado.alberto.org.variables;

public enum PaymentMethod {

    PAYPAL("PayPal"),
    CASH("Cash"),
    CREDIT_CARD("CreditCard"),
    TRANSFER("Transfer");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String getDescripcion() {
        return description;
    }

}
