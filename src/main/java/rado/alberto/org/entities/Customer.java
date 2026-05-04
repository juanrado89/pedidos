package rado.alberto.org.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer", nullable = false)
    private Long id;

    @NotNull
    @Size(max = 80)
    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @NotNull
    @Size(max = 100)
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

    @OneToMany(mappedBy = "customer")
    private List<Payment> payments;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @Size(max = 20)
    @Column(name = "telephone", length = 20)
    private String telephone;

    @NotNull
    @Email
    @Size(max = 140)
    @Column(name = "email", nullable = false, unique = true, length = 140)
    private String email;

    @NotNull
    @Size(max = 255)
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer other)) return false;
        return id != 0 && id.equals(other.id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Customer{");
        sb.append("id = ").append(id);
        sb.append("\nname = ").append(name);
        sb.append("\nlastName = ").append(lastName);
        if (addresses != null) {
            int count = 1;
            for (Address address : addresses) {
                sb.append("\naddress nº ").append(count).append(" = ").append(address.toString());
                count++;
            }
        }
        if(this.telephone != null && !this.telephone.isBlank()){
            sb.append("\ntelephone = ").append(telephone);
        }
        sb.append("\nemail = ").append(email);
        sb.append("\npassword = ").append(password);
        sb.append('}');
        return sb.toString();
    }
}