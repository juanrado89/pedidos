package rado.alberto.org.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import rado.alberto.org.entities.Customer;
import rado.alberto.org.variables.Role;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Customer}
 */
public record CustomerUpdateDto(
        Long id, @Size(max = 80) String name,
        @Size(max = 100) String lastName,
        @Valid List<AddressDto> addresses,
        @Size(max = 20) String telephone,
        @Size(max = 140) @Email String email,
        Role role,
        @Size(max = 255) String password) implements Serializable {
}