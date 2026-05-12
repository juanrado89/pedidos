package rado.alberto.org.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rado.alberto.org.dto.AuthResponse;
import rado.alberto.org.dto.LoginDto;
import rado.alberto.org.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }
}
