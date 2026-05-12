package rado.alberto.org.variables;

public enum Role {
    ADMIN("Administrator"),
    CUSTOMER("Customer");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
    
}
