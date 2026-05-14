package rado.alberto.org.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rado.alberto.org.dto.CustomerCreateDto;
import rado.alberto.org.dto.CustomerResponseDto;
import rado.alberto.org.dto.CustomerUpdateDto;
import rado.alberto.org.entities.Address;
import rado.alberto.org.entities.Customer;
import rado.alberto.org.exceptions.CustomerAlreadyExistException;
import rado.alberto.org.exceptions.CustomerNotFoundException;
import rado.alberto.org.mapper.AddressMapper;
import rado.alberto.org.mapper.CustomerMapper;
import rado.alberto.org.repositories.CustomerRepository;
import rado.alberto.org.variables.Role;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, AddressMapper addressMapper, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.addressMapper = addressMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<CustomerResponseDto> getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        return Optional.of(customerMapper.toDtoResponse(customer));
    }

    public Optional<CustomerResponseDto> getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(CustomerNotFoundException::new);
        return Optional.of(customerMapper.toDtoResponse(customer));
    }

    public Optional<CustomerResponseDto> createCustomer(CustomerCreateDto customerCreateDto) {
        Optional<Customer> search = customerRepository.findByEmail(customerCreateDto.email());
        if (!search.isEmpty()) {
            throw new CustomerAlreadyExistException();
        }
        Customer customer = new Customer();
        customer.setName(customerCreateDto.name());
        customer.setLastName(customerCreateDto.lastName());
        customer.setRole(Role.CUSTOMER);
        customer.setTelephone(customerCreateDto.telephone());
        List<Address> addressList = addressMapper.toEntities(customerCreateDto.addresses());
        for (Address address : addressList) {
            address.setCustomer(customer);
        }
        customer.setAddresses(addressList);
        customer.setEmail(customerCreateDto.email());
        customer.setPassword(passwordEncoder.encode(customerCreateDto.password()));
        customerRepository.save(customer);
        return Optional.of(customerMapper.toDtoResponse(customer));
    }

    public Optional<CustomerResponseDto> updateCustomer(CustomerUpdateDto customerDto) {
        Optional<Customer> search = customerRepository.findByEmail(customerDto.email());
        if (search.isEmpty()) {
            throw new CustomerNotFoundException();
        }
        Customer customer = search.get();
        if(customerDto.name() != null && !customerDto.name().isEmpty()) {
            customer.setName(customerDto.name());
        }
        if(customerDto.lastName() != null && !customerDto.lastName().isEmpty()) {
            customer.setLastName(customerDto.lastName());
        }
        if(customerDto.telephone() != null && !customerDto.telephone().isEmpty()) {
            customer.setTelephone(customerDto.telephone());
        }
        if(customerDto.addresses() != null && !customerDto.addresses().isEmpty()) {
            List<Address> addressList = addressMapper.toEntities(customerDto.addresses());
            for (Address address : addressList) {
                address.setCustomer(customer);
            }
            customer.setAddresses(addressList);
        }
        if(customerDto.password() != null && !customerDto.password().isEmpty()) {
            customer.setPassword(passwordEncoder.encode(customerDto.password()));
        }
        if(customerDto.email() != null && !customerDto.email().isEmpty()) {
            customer.setEmail(customerDto.email());
        }
        customerRepository.save(customer);
        return Optional.of(customerMapper.toDtoResponse(customer));
    }

    public void deleteCustomer(Long id) {
        Optional<Customer> search = customerRepository.findById(id);
        if (search.isEmpty()) {
            throw new CustomerNotFoundException();
        }
        customerRepository.deleteById(id);
    }
}
