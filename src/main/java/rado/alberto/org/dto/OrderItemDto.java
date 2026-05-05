package rado.alberto.org.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import rado.alberto.org.entities.OrderItem;
import rado.alberto.org.variables.Tax;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link OrderItem}
 */
public record OrderItemDto(Long id, ProductDto product, @Min(1) int quantity,
                           @NotNull @Digits(integer = 20, fraction = 2) BigDecimal price,
                           @Digits(integer = 2, fraction = 2) BigDecimal discount, @NotNull Tax tax,
                           @NotNull @Digits(integer = 20, fraction = 2) BigDecimal totalPrice) implements Serializable {
}