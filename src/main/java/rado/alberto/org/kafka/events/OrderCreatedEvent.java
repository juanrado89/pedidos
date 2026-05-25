package rado.alberto.org.kafka.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderCreatedEvent(
        Long orderId,
        Long customerId,
        BigDecimal totalAmount,
        LocalDateTime createdAt
) {
}