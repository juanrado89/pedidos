package rado.alberto.org.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

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
    @Size(min = 0,max = 40)
    @Basic
    @Column(name = "country",nullable = false)
    private String country;

    @NotNull
    @Size(min = 0,max = 40)
    @Basic
    @Column(name = "city",nullable = false)
    private String city;

    @NotNull
    @Size(min = 0,max = 40)
    @Basic
    @Column(name = "state",nullable = false)
    private String state;

    @NotNull
    @Size(min = 0,max = 14)
    @Basic
    @Column(name = "zip",nullable = false)
    private String zip;

    @NotNull
    @Size(min = 0,max = 100)
    @Basic
    @Column(name = "street",nullable = false)
    private String street;

    @Size(min = 0,max = 3)
    @Basic
    @Column(name = "floor",nullable = true)
    private String floor;

    @Size(min = 0,max = 40)
    @Basic
    @Column(name = "building",nullable = false)
    private String building;
    @Size(min = 0,max = 5)
    @Basic
    private String door;
}