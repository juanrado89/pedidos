package rado.alberto.org.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rado.alberto.org.variables.Role;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "administrator")
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotNull
    @Size(min = 1, max = 120)
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "surename", nullable = false, length = 120)
    private String surname;

    @NotNull
    @Size(min = 1, max = 150)
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "email",unique = true, nullable = false)
    private String email;

    @NotNull
    @Basic(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role =  Role.ADMIN;

    @NotNull
    @Basic(fetch = FetchType.LAZY)
    @Size(min = 12, max = 70)
    @Column(name = "password", nullable = false, length = 70)
    private String password;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Administrator other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Administrator{");
        sb.append("id = ").append(id);
        sb.append("\nname = ").append(name);
        sb.append("\nsurname = ").append(surname);
        sb.append("\nemail = ").append(email);
        sb.append("\nrole = ").append(role);
        sb.append('}');
        return sb.toString();
    }
}