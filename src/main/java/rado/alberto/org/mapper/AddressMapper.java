package rado.alberto.org.mapper;

import org.mapstruct.Mapper;
import rado.alberto.org.dto.AddressCreateDto;
import rado.alberto.org.dto.AddressDto;
import rado.alberto.org.dto.AddressUpdateDto;
import rado.alberto.org.entities.Address;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntity(AddressDto addressDto);
    AddressDto toDto(Address address);

    List<AddressDto> toDtos(List<Address> addressList);
    List<Address> toEntities(List<AddressDto> addressDtoList);

    Address toEntity(AddressCreateDto dto);

    Address toEntity(AddressUpdateDto dto);
}
