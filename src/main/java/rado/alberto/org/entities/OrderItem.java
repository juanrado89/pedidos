package rado.alberto.org.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    private Product product;

    @NotNull
    @Basic
    private int quantity;

    @NotNull
    @Basic
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull
    @Basic
    @Column(name = "discount", precision = 10, scale = 2)
    private BigDecimal discount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tax", nullable = false, length = 30)
    private Tax tax;

    @NotNull
    @Basic
    @Column(name = "totalPrice", nullable = false, precision = 10, scale = 2)
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderItem{");
        sb.append("id=").append(id);
        sb.append("\norder = ").append(order);
        sb.append("\nproduct = ").append(product);
        sb.append("\nquantity = ").append(quantity);
        sb.append("\nprice = ").append(price.toString());
        if(discount != null) {
            sb.append("\ndiscount = ").append(discount.toString());
        }
        sb.append("\ntax = ").append(tax.getRate().toString());
        sb.append("\ntotalPrice = ").append(totalPrice.toString());
        sb.append('}');
        return sb.toString();
    }
}