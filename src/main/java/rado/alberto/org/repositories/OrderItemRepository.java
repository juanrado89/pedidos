package rado.alberto.org.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rado.alberto.org.entities.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("select o from OrderItem o where o.order.id = ?1")
    List<OrderItem> findByOrder_Id(Long id);
}
