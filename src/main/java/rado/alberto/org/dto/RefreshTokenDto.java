package rado.alberto.org.dto;

import jakarta.validation.constraints.NotBlank;
import rado.alberto.org.entities.RefreshToken;

import java.io.Serializable;

/**
 * DTO for {@link RefreshToken}
 */
public record RefreshTokenDto(@NotBlank String token) implements Serializable {
}