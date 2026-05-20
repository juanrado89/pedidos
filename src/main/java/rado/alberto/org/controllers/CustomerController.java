package rado.alberto.org.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rado.alberto.org.dto.CustomerCreateDto;
import rado.alberto.org.dto.CustomerResponseDto;
import rado.alberto.org.dto.CustomerUpdateDto;
import rado.alberto.org.services.CustomerService;

import java.util.Optional;

@RestController()
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController (CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CustomerResponseDto> findById(@PathVariable Long id) {
        CustomerResponseDto result = customerService.getCustomerById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponseDto> findByEmail(@PathVariable String email) {
        CustomerResponseDto result = customerService.getCustomerByEmail(email);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/")
    public  ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody @Valid CustomerCreateDto customerCreateDto) {
        CustomerResponseDto result = customerService.createCustomer(customerCreateDto);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize(("hasRole('CUSTOMER')"))
    @PutMapping("/")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@RequestBody @Valid CustomerUpdateDto customerDto) {
        CustomerResponseDto result = customerService.updateCustomer(customerDto);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize(("hasRole('CUSTOMER')"))
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

}
