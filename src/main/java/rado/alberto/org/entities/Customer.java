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
    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(max = 100)
    @Basic
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "addresses")
    private List<Address> addresses;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "orders")
    private List<Order> orders;


    @Size(max = 14)
    @Basic
    @Column(name = "telephone")
    private int telephone;

    @NotNull
    @Size(max = 140)
    @Basic
    @Email
    @Column(name = "name", nullable = false)
    private String email;

    @NotNull
    @Size(max = 50)
    @Basic
    @Column(name = "password", nullable = false)
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
        int count = 1;
        for (Address address : addresses) {
            sb.append("\naddress nº ").append(count).append(" = ").append(address.toString());
            count++;
        }
        if(this.telephone != -1){
            sb.append("\ntelephone = ").append(telephone);
        }
        sb.append("\nemail = ").append(email);
        sb.append("\npassword = ").append(password);
        sb.append('}');
        return sb.toString();
    }
}