package rado.alberto.org.variables;

import lombok.Getter;

@Getter
public enum ProductCategory {

    GENERAL("General", Tax.GENERAL),
    FOOD("Alimentación", Tax.REDUCED),
    BOOK("Libros", Tax.SUPER_REDUCED),
    MEDICINE("Medicamentos", Tax.SUPER_REDUCED),
    SERVICE("Servicios", Tax.GENERAL);

    private final String displayName;
    private final Tax tax;

    ProductCategory(String displayName, Tax tax) {
        this.displayName = displayName;
        this.tax = tax;
    }
}
