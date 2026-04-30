package rado.alberto.org.pricing;

import rado.alberto.org.entities.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public class PricingCalculator {

    public static BigDecimal priceCalculator(List<OrderItem> orderItems) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            totalAmount = totalAmount.add(orderItem.getTotalPrice());
        }
        return totalAmount;
    }
}
