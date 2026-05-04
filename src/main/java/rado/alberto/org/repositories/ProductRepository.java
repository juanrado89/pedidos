package rado.alberto.org.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rado.alberto.org.entities.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
