package rado.alberto.org.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rado.alberto.org.dto.CustomerCreateDto;
import rado.alberto.org.dto.CustomerResponseDto;
import rado.alberto.org.entities.Customer;

@Mapper(componentModel = "spring")
public interface CustomerOrderMapper {
    Customer toEntity(CustomerResponseDto customerResponseDto);
    Customer toEntity(CustomerCreateDto customerCreateDto);

    @Mapping(target = "addresses", ignore = true)
    CustomerResponseDto toDtoResponse(Customer customer);

    @Mapping(target = "addresses", ignore = true)
    CustomerCreateDto toDtoCreate(Customer customer);
}
