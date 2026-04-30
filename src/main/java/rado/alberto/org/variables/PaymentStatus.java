package rado.alberto.org.variables;

public enum PaymentStatus {

    PENDING("Pending"),
    PAID("Paid"),
    CANCELED("Canceled");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescripcion() {
        return description;
    }

}
