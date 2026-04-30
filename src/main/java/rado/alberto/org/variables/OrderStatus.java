package rado.alberto.org.variables;

public enum OrderStatus {

    PENDIENTE("Pendiente"),
    EN_PROCESO("En proceso"),
    ENVIADO("Enviado"),
    ENTREGADO("Entregado"),
    CANCELADO("Cancelado");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescripcion() {
        return description;
    }
}
