package rado.alberto.org.security;

import rado.alberto.org.variables.Role;

public record AuthenticatedUser(
        Long id,
        String email,
        Role role
) {
}
