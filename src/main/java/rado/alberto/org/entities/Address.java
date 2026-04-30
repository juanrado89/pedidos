package rado.alberto.org.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address", nullable = false)
    private Long id;

    @NotNull
    @Size(max = 40)
    @Basic
    @Column(name = "country",nullable = false)
    private String country;

    @NotNull
    @Size(max = 40)
    @Basic
    @Column(name = "city",nullable = false)
    private String city;

    @NotNull
    @Size(max = 40)
    @Basic
    @Column(name = "state",nullable = false)
    private String state;

    @NotNull
    @Size(max = 14)
    @Basic
    @Column(name = "zip",nullable = false)
    private String zip;

    @NotNull
    @Size(max = 100)
    @Basic
    @Column(name = "street",nullable = false)
    private String street;

    @NotNull
    @Size(max = 3)
    @Basic
    @Column(name = "floor",nullable = false)
    private String floor;

    @NotNull
    @Size(max = 40)
    @Basic
    @Column(name = "building",nullable = false)
    private String building;

    @NotNull
    @Size(max = 5)
    @Basic
    @Column(name = "door",nullable = false)
    private String door;

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
        if (!(o instanceof Address other)) return false;
        return id != 0 && id.equals(other.id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Address{");
        sb.append("id = ").append(id);
        sb.append("\ncountry = ").append(country);
        sb.append("\ncity = ").append(city);
        sb.append("\nstate = ").append(state);
        sb.append("\nzip = ").append(zip);
        sb.append("\nstreet = ").append(street);
        sb.append("\nfloor = ").append(floor);
        sb.append("\nbuilding = ").append(building);
        sb.append("\ndoor = ").append(door);
        sb.append('}');
        return sb.toString();
    }
}