package rado.alberto.org.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import rado.alberto.org.entities.Address;

import java.io.Serializable;

/**
 * DTO for {@link Address}
 */
public record AddressDto(Long id, @NotNull @Size(max = 40) String country, @NotNull @Size(max = 40) String city,
                         @NotNull @Size(max = 40) String state, @NotNull @Size(max = 14) String zip,
                         @NotNull @Size(max = 100) String street, @NotNull @Size(max = 3) String floor,
                         @NotNull @Size(max = 40) String building,
                         @NotNull @Size(max = 5) String door) implements Serializable {
}