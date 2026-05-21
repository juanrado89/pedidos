package rado.alberto.org.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import rado.alberto.org.config.JwtProperties;
import rado.alberto.org.entities.RefreshToken;
import rado.alberto.org.exceptions.InvalidCredentialsException;
import rado.alberto.org.repositories.RefreshTokenRepository;
import rado.alberto.org.variables.Role;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperties jwtProperties;

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository,
            JwtProperties jwtProperties
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtProperties = jwtProperties;
    }

    @Transactional
    public RefreshToken createRefreshToken(Long userId, String email, Role role) {
        refreshTokenRepository.deleteByIdUserAndRole(userId, role);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setIdUser(userId);
        refreshToken.setEmail(email);
        refreshToken.setRole(role);
        refreshToken.setCreatedAt(LocalDateTime.now());
        refreshToken.setExpiresAt(
                LocalDateTime.now().plus(
                        jwtProperties.refreshTokenExpirationMs(),
                        ChronoUnit.MILLIS
                )
        );
        refreshToken.setRevoked(false);
        refreshToken.setExpired(false);

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public RefreshToken refresh(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(InvalidCredentialsException::new);

        LocalDateTime now = LocalDateTime.now();

        if (refreshToken.isRevoked()
                || refreshToken.isExpired()
                || refreshToken.getExpiresAt().isBefore(now)) {

            refreshToken.setExpired(true);
            refreshTokenRepository.save(refreshToken);

            throw new InvalidCredentialsException();
        }

        refreshToken.setExpiresAt(
                now.plus(jwtProperties.refreshTokenExpirationMs(), ChronoUnit.MILLIS)
        );

        return refreshTokenRepository.save(refreshToken);
    }

    public void revoke(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(InvalidCredentialsException::new);

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }
}
