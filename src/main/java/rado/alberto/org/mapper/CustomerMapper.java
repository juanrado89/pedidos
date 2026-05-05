package rado.alberto.org.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rado.alberto.org.dto.CustomerCreateDto;
import rado.alberto.org.dto.CustomerResponseDto;
import rado.alberto.org.entities.Customer;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface CustomerMapper {
    Customer toEntity(CustomerResponseDto customerResponseDto);
    Customer toEntity(CustomerCreateDto customerCreateDto);

    CustomerResponseDto toDtoResponse(Customer customer);
    CustomerCreateDto toDtoCreate(Customer customer);
}
