package rado.alberto.org.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rado.alberto.org.entities.Administrator;

import java.util.Optional;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    Optional<Administrator> findByEmail(String email);
}
