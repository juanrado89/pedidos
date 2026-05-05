package rado.alberto.org.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import rado.alberto.org.entities.Customer;

import java.io.Serializable;

/**
 * DTO for {@link Customer}
 */
public record CustomerOrderResponseDto(
        Long id, @NotNull @Size(max = 80) String name,
        @NotNull @Size(max = 100) String lastName,
        @Size(max = 20) String telephone,
        @NotNull @Size(max = 140) @Email String email) implements Serializable {
}