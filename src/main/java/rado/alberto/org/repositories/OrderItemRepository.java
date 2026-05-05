package rado.alberto.org.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rado.alberto.org.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
