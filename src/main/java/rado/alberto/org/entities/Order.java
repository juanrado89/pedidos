package rado.alberto.org.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import rado.alberto.org.variables.OrderStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order", nullable = false)
    private Long id;

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;

    @NotNull
    @Basic
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @NotNull
    @Basic
    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @NotNull
    @Basic
    @Column(name = "orderDate", nullable = false)
    private LocalDateTime orderDate;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order other)) return false;
        return id != 0 && id.equals(other.id);
    }
}