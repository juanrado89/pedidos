package rado.alberto.org.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import rado.alberto.org.entities.Administrator;
import rado.alberto.org.variables.Role;

import java.io.Serializable;

/**
 * DTO for {@link Administrator}
 */
public record AdministratorResponseDto(Long id, @NotNull @Size(min = 1, max = 50) String name,
                                       @NotNull @Size(min = 1, max = 120) String surname,
                                       @NotNull @Size(min = 1, max = 150) String email,
                                       @NotNull Role role) implements Serializable {
}