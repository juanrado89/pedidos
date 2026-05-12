package rado.alberto.org.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginDto(
        @NotNull @Email String email,
        @NotNull String password
) {
}
