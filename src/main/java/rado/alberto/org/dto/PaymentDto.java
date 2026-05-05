package rado.alberto.org.dto;

import jakarta.validation.constraints.NotNull;
import rado.alberto.org.entities.Payment;
import rado.alberto.org.variables.PaymentMethod;
import rado.alberto.org.variables.PaymentStatus;

import java.io.Serializable;

/**
 * DTO for {@link Payment}
 */
public record PaymentDto(Long id, @NotNull PaymentMethod paymentMethod, @NotNull PaymentStatus paymentStatus,
                         @NotNull OrderDto order, CustomerResponseDto customer) implements Serializable {
}