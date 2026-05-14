package rado.alberto.org.controllers;

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
        Optional<CustomerResponseDto> result = customerService.getCustomerById(id);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponseDto> findByEmail(@PathVariable String email) {
        Optional<CustomerResponseDto> result = customerService.getCustomerByEmail(email);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public  ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerCreateDto customerCreateDto) {
        Optional<CustomerResponseDto> result = customerService.createCustomer(customerCreateDto);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PreAuthorize(("hasRole('CUSTOMER')"))
    @PutMapping("/")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@RequestBody CustomerUpdateDto customerDto) {
        Optional<CustomerResponseDto> result = customerService.updateCustomer(customerDto);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PreAuthorize(("hasRole('CUSTOMER')"))
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

}
