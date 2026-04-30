package rado.alberto.org.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import rado.alberto.org.variables.Tax;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_item", nullable = false)
    private Long id;

    private Order order;
    private Product product;
    private int quantity;
    private BigDecimal price;
    private BigDecimal discount;
    private Tax tax;
    private BigDecimal totalPrice;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem other)) return false;
        return id != 0 && id.equals(other.id);
    }
}