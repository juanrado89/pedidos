package rado.alberto.org.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import rado.alberto.org.entities.Product;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    Optional<Product> findBySku(String sku);
    boolean existsBySku(String sku);
    Page<Product> findAll(Pageable pageable);
}
