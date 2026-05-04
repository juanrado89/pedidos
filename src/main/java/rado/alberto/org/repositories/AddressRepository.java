package rado.alberto.org.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rado.alberto.org.entities.Address;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long>{
    List<Address> findAddressByCustomerId(long customerId);
}
