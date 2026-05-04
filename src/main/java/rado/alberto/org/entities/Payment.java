package rado.alberto.org.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import rado.alberto.org.variables.PaymentMethod;
import rado.alberto.org.variables.PaymentStatus;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_payment", nullable = false)
    private Long id;

    @NotNull
    @Basic
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @NotNull
    @Basic
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @OneToOne
    @JoinColumn(name = "id_order")
    @NotNull
    private Order order;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment other)) return false;
        return id != 0 && id.equals(other.id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Payment{");
        sb.append("id=").append(id);
        sb.append("\npaymentMethod = ").append(paymentMethod.getDescripcion());
        sb.append("\npaymentStatus = ").append(paymentStatus.getDescripcion());
        sb.append("\norder = ").append(order);
        sb.append("\ncustomer = ").append(customer);
        sb.append('}');
        return sb.toString();
    }
}