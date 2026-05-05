package rado.alberto.org.pricing;

import rado.alberto.org.entities.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public class PricingCalculator {

    private PricingCalculator() {

    }
    public static BigDecimal calculateTotalPrice(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public static BigDecimal calculateOrderItemTotal(OrderItem item) {

        BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());

        BigDecimal subtotal = item.getPrice().multiply(quantity);

        if (item.getDiscount() != null) {
            subtotal = subtotal.multiply(BigDecimal.ONE.subtract(item.getDiscount()));
        }

        BigDecimal taxAmount = subtotal.multiply(item.getTax().getRate());

        return subtotal.add(taxAmount);
    }
}
