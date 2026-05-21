package rado.alberto.org.dto;

import jakarta.validation.constraints.NotNull;
import rado.alberto.org.variables.OrderStatus;

import java.io.Serializable;

/**
 * DTO for {@link rado.alberto.org.entities.Order}
 */
public record OrderStatusDto(@NotNull OrderStatus orderStatus) implements Serializable {
}