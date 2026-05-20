package rado.alberto.org.mapper;

import org.mapstruct.Mapper;
import rado.alberto.org.dto.CustomerCreateDto;
import rado.alberto.org.dto.CustomerOrderResponseDto;
import rado.alberto.org.dto.CustomerResponseDto;
import rado.alberto.org.dto.CustomerUpdateDto;
import rado.alberto.org.entities.Customer;

import java.util.List;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface CustomerMapper {
    Customer toEntity(CustomerResponseDto customerResponseDto);
    Customer toEntity(CustomerCreateDto customerCreateDto);

    CustomerResponseDto toDtoResponse(Customer customer);
    CustomerCreateDto toDtoCreate(Customer customer);

    Customer toEntity(CustomerUpdateDto o);

    Customer toEntity(CustomerOrderResponseDto customer);

    List<CustomerResponseDto> toDtosResponse(List<Customer> result);
}
