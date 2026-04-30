package rado.alberto.org.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

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
    @Size(min = 0,max = 80)
    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(min = 0,max = 100)
    @Basic
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Size(min = 0,max = 80)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address")
    private Address address;


    @Size(min = 0,max = 14)
    @Basic
    @Column(name = "telephone", nullable = true)
    private int telephone;

    @NotNull
    @Size(min = 0,max = 140)
    @Basic
    @Email
    @Column(name = "name", nullable = false)
    private String email;

    @NotNull
    @Size(min = 0,max = 50)
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
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", address=").append(address);
        sb.append(", telephone=").append(telephone);
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}