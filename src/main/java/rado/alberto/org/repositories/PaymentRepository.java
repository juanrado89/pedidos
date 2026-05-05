package rado.alberto.org.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rado.alberto.org.entities.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByCustomer_Id(Long id);
}
