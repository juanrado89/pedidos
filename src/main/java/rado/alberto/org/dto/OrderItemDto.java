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
public record OrderItemDto(Long id, ProductDto product, @Min(1) int quantity
                           ) implements Serializable {
}