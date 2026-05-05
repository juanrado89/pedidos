package rado.alberto.org.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rado.alberto.org.entities.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
