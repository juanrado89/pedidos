package rado.alberto.org.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rado.alberto.org.dto.AuthResponse;
import rado.alberto.org.dto.LoginDto;
import rado.alberto.org.dto.RefreshTokenDto;
import rado.alberto.org.entities.Administrator;
import rado.alberto.org.entities.Customer;
import rado.alberto.org.entities.RefreshToken;
import rado.alberto.org.exceptions.InvalidCredentialsException;
import rado.alberto.org.repositories.AdministratorRepository;
import rado.alberto.org.repositories.CustomerRepository;

@Service
public class AuthService {

    private final CustomerRepository customerRepository;
    private final AdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(
            CustomerRepository customerRepository,
            AdministratorRepository administratorRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService, RefreshTokenService refreshTokenService
    ) {
        this.customerRepository = customerRepository;
        this.administratorRepository = administratorRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    public AuthResponse login(LoginDto loginDto) {
        return administratorRepository.findByEmail(loginDto.email())
                .map(admin -> loginAdministrator(admin, loginDto.password()))
                .orElseGet(() -> customerRepository.findByEmail(loginDto.email())
                        .map(customer -> loginCustomer(customer, loginDto.password()))
                        .orElseThrow(() -> new InvalidCredentialsException()));
    }

    private AuthResponse loginAdministrator(Administrator admin, String rawPassword) {
        if (!passwordEncoder.matches(rawPassword, admin.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = jwtService.generateAccessToken(
                admin.getId(),
                admin.getEmail(),
                admin.getRole()
        );

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(
                admin.getId(),
                admin.getEmail(),
                admin.getRole()
        );

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    private AuthResponse loginCustomer(Customer customer, String rawPassword) {
        if (!passwordEncoder.matches(rawPassword, customer.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = jwtService.generateAccessToken(
                customer.getId(),
                customer.getEmail(),
                customer.getRole()
        );

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(
                customer.getId(),
                customer.getEmail(),
                customer.getRole()
        );

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    public AuthResponse refreshToken(RefreshTokenDto dto) {
        RefreshToken refreshToken = refreshTokenService.refresh(dto.token());

        String accessToken = jwtService.generateAccessToken(
                refreshToken.getIdUser(),
                refreshToken.getEmail(),
                refreshToken.getRole()
        );

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    public void logout(RefreshTokenDto dto) {
        refreshTokenService.revoke(dto.token());
    }
}
