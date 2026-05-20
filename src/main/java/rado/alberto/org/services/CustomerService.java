package rado.alberto.org.services;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rado.alberto.org.dto.CustomerCreateDto;
import rado.alberto.org.dto.CustomerResponseDto;
import rado.alberto.org.dto.CustomerUpdateDto;
import rado.alberto.org.entities.Address;
import rado.alberto.org.entities.Customer;
import rado.alberto.org.exceptions.CustomerAlreadyExistException;
import rado.alberto.org.exceptions.CustomerNotFoundException;
import rado.alberto.org.exceptions.InvalidCustomerException;
import rado.alberto.org.mapper.CustomerMapper;
import rado.alberto.org.repositories.CustomerRepository;
import rado.alberto.org.variables.Role;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerResponseDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        return customerMapper.toDtoResponse(customer);
    }

    public CustomerResponseDto getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(CustomerNotFoundException::new);
        return customerMapper.toDtoResponse(customer);
    }

    @Transactional
    public CustomerResponseDto createCustomer(CustomerCreateDto customerCreateDto) {
        Optional<Customer> search = customerRepository.findByEmail(customerCreateDto.email());
        if (search.isPresent()) {
            throw new CustomerAlreadyExistException();
        }
        Customer customer = new Customer();
        return customerMapper.toDtoResponse(customerRepository.save(verifyCustomer(customerCreateDto, customer)));
    }

    @Transactional
    public CustomerResponseDto updateCustomer(CustomerUpdateDto customerDto, String mail) {
        Optional<Customer> search = customerRepository.findByEmail(mail);
        if (search.isEmpty()) {
            throw new CustomerNotFoundException();
        }
        return customerMapper.toDtoResponse(customerRepository.save(verifyCustomer(customerDto, search.get())));
    }

    @Transactional
    public void deleteCustomer(Long id) {
        Optional<Customer> search = customerRepository.findById(id);
        if (search.isEmpty()) {
            throw new CustomerNotFoundException();
        }
        customerRepository.deleteById(id);
    }
    private Customer verifyCustomer(Object o, Customer customer) {
        Customer customerDto;
        if (o instanceof CustomerCreateDto) {
            customerDto = customerMapper.toEntity((CustomerCreateDto) o);
            customer.setRole(Role.CUSTOMER);
        }else if (o instanceof CustomerUpdateDto) {
            customerDto = customerMapper.toEntity((CustomerUpdateDto) o);
            customer.setId(customerDto.getId());
        }else{
            throw new InvalidCustomerException();
        }
        if(customerDto.getName() != null && !customerDto.getName().isEmpty()) {
            customer.setName(customerDto.getName());
        }
        if(customerDto.getLastName() != null && !customerDto.getLastName().isEmpty()) {
            customer.setLastName(customerDto.getLastName());
        }
        if(customerDto.getTelephone() != null && !customerDto.getTelephone().isEmpty()) {
            customer.setTelephone(customerDto.getTelephone());
        }
        if(customerDto.getAddresses() != null && !customerDto.getAddresses().isEmpty()) {
            List<Address> addressList = customerDto.getAddresses();
            for (Address address : addressList) {
                address.setCustomer(customer);
            }
            customer.setAddresses(addressList);
        }
        if(customerDto.getPassword() != null && !customerDto.getPassword().isEmpty()) {
            customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        }
        if(customerDto.getEmail() != null && !customerDto.getEmail().isEmpty()) {
            customer.setEmail(customerDto.getEmail());
        }
        return customer;
    }

    public List<CustomerResponseDto> getAllCustomers() {
        List<Customer> result = customerRepository.findAll();
        return customerMapper.toDtosResponse(result);
    }
}
