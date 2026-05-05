package rado.alberto.org.dto;

import rado.alberto.org.entities.RefreshToken;

import java.io.Serializable;

/**
 * DTO for {@link RefreshToken}
 */
public record RefreshTokenDto(String token) implements Serializable {
}