package rado.alberto.org.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import rado.alberto.org.entities.Order;
import rado.alberto.org.variables.OrderStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link Order}
 */
public record OrderDto(Long id,
                       CustomerOrderResponseDto customer,
                       AddressDto shippingAddress,
                       AddressDto billingAddress,
                       OrderStatus orderStatus,
                       @NotNull @Digits(integer = 20, fraction = 2) BigDecimal totalAmount,
                       @NotNull LocalDateTime orderDate,
                       List<OrderItemResponseDto> items) implements Serializable {
}