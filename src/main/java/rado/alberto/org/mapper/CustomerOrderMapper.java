package rado.alberto.org.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rado.alberto.org.dto.CustomerCreateDto;
import rado.alberto.org.dto.CustomerOrderResponseDto;
import rado.alberto.org.dto.CustomerResponseDto;
import rado.alberto.org.entities.Customer;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerOrderMapper {

    Customer toEntity(CustomerOrderResponseDto customerOrderResponseDto);
    CustomerOrderResponseDto toDto(Customer customer);

    List<CustomerOrderResponseDto> toDtos(List<Customer> customers);
    List<Customer> toEntities(List<CustomerOrderResponseDto> customerOrderResponseDtoList);

}
