package rado.alberto.org.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import rado.alberto.org.entities.Product;
import rado.alberto.org.variables.ProductCategory;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Product}
 */
public record ProductDto(Long id, @NotNull @Size(max = 120) String name, @NotNull @Size(max = 3000) String description,
                         String image, @NotNull String sku,
                         @NotNull @Digits(integer = 20, fraction = 2) BigDecimal price,
                         @NotNull ProductCategory category, @NotNull @Min(0) Integer stock,
                         @Digits(integer = 2, fraction = 2) BigDecimal discount) implements Serializable {
}