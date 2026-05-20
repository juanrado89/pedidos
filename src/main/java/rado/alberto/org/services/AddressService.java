package rado.alberto.org.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import rado.alberto.org.dto.AddressCreateDto;
import rado.alberto.org.dto.AddressDto;
import rado.alberto.org.dto.AddressUpdateDto;
import rado.alberto.org.entities.Address;
import rado.alberto.org.entities.Customer;
import rado.alberto.org.exceptions.AddressNotFoundException;
import rado.alberto.org.exceptions.CustomerNotFoundException;
import rado.alberto.org.exceptions.InvalidAddressException;
import rado.alberto.org.mapper.AddressMapper;
import rado.alberto.org.repositories.AddressRepository;
import rado.alberto.org.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final AddressMapper addressMapper;


    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
        this.addressMapper = addressMapper;
    }

    public AddressDto getAddressById(long id, Long idCustomer) {
        Optional<Address> result = addressRepository.findById(id);
        if(result.isEmpty()) {
            throw new AddressNotFoundException();
        }
        if(!result.get().getCustomer().getId().equals(idCustomer)) {
            throw new CustomerNotFoundException();
        }
        return addressMapper.toDto(result.get());
    }

    public List<AddressDto> getAllAddressByCustomerId(long idCustomer) {
        Optional<Customer> searchCustomer = customerRepository.findById(idCustomer);
        if (searchCustomer.isEmpty()) {
            throw new CustomerNotFoundException();
        }
        List<Address> search = addressRepository.findAddressByCustomerId(idCustomer);
        return addressMapper.toDtos(search);
    }

    @Transactional
    public AddressDto createAddress(AddressCreateDto addressDto, Long id) {
        Optional<Customer> searchCustomer = customerRepository.findById(id);
        if (searchCustomer.isEmpty()) {
            throw new CustomerNotFoundException();
        }
        Address address = new  Address();
        address.setCustomer(searchCustomer.get());
        Address newAddress = verifyAddress(addressDto, address);
        return addressMapper.toDto(addressRepository.save(newAddress));
    }

    @Transactional
    public AddressDto updateAddress(AddressUpdateDto addressDto, Long id) {
        Optional<Address> search = addressRepository.findById(addressDto.id());
        if (search.isEmpty()) {
            throw new AddressNotFoundException();
        }
        if(!search.get().getCustomer().getId().equals(id)){
            throw new AddressNotFoundException();
        }
        Address address = search.get();
        Address update = verifyAddress(addressDto, address);
        return addressMapper.toDto(addressRepository.save(update));
    }

    @Transactional
    public void deleteAddressById(long id, Long idCustomer) {
        Optional<Address> search = addressRepository.findById(id);
        if (search.isEmpty()) {
            throw new AddressNotFoundException();
        }
        if (!search.get().getCustomer().getId().equals(idCustomer)) {
            throw new AddressNotFoundException();
        }
        addressRepository.deleteById(id);
    }

    private Address verifyAddress(Object o, Address address) {
        Address addressDto;
        if (o instanceof AddressCreateDto dto) {
            addressDto = addressMapper.toEntity(dto);
        } else if (o instanceof AddressUpdateDto dto) {
            addressDto = addressMapper.toEntity(dto);
            addressDto.setId(address.getId());
        } else {
            throw new InvalidAddressException();
        }
        if(addressDto.getState() != null && !addressDto.getState().isEmpty()) {
            address.setState(addressDto.getState());
        }
        if(addressDto.getCity() != null && !addressDto.getCity().isEmpty()) {
            address.setCity(addressDto.getCity());
        }
        if(addressDto.getCountry() != null && !addressDto.getCountry().isEmpty()) {
            address.setCountry(addressDto.getCountry());
        }
        if(addressDto.getZip() != null && !addressDto.getZip().isEmpty()) {
            address.setZip(addressDto.getZip());
        }
        if(addressDto.getFloor() != null && !addressDto.getFloor().isEmpty()) {
            address.setFloor(addressDto.getFloor());
        }
        if(addressDto.getStreet() != null && !addressDto.getStreet().isEmpty()) {
            address.setStreet(addressDto.getStreet());
        }
        if(addressDto.getBuilding() != null && !addressDto.getBuilding().isEmpty()) {
            address.setBuilding(addressDto.getBuilding());
        }
        if(addressDto.getDoor() != null && !addressDto.getDoor().isEmpty()) {
            address.setDoor(addressDto.getDoor());
        }
        return address;
    }
}
