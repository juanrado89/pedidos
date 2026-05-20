package rado.alberto.org.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import rado.alberto.org.dto.CustomerCreateDto;
import rado.alberto.org.dto.CustomerResponseDto;
import rado.alberto.org.dto.CustomerUpdateDto;
import rado.alberto.org.security.AuthenticatedUser;
import rado.alberto.org.services.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController (CustomerService customerService) {
        this.customerService = customerService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/me")
    public ResponseEntity<CustomerResponseDto> findMe(@AuthenticationPrincipal AuthenticatedUser user){
        CustomerResponseDto result = customerService.getCustomerById(user.id());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/me/email")
    public ResponseEntity<CustomerResponseDto> findByEmail(@AuthenticationPrincipal AuthenticatedUser user) {
        CustomerResponseDto result = customerService.getCustomerByEmail(user.email());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/{idCustomer}")
    public ResponseEntity<CustomerResponseDto> findByCustomerById(@PathVariable Long idCustomer) {
        CustomerResponseDto result = customerService.getCustomerById(idCustomer);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/mail/{mail}")
    public ResponseEntity<CustomerResponseDto> findByMail(@PathVariable String mail) {
        CustomerResponseDto result = customerService.getCustomerByEmail(mail);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/all")
    public ResponseEntity<List<CustomerResponseDto>> findAllCustomers() {
        List<CustomerResponseDto> result = customerService.getAllCustomers();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/")
    public  ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody @Valid CustomerCreateDto customerCreateDto) {
        CustomerResponseDto result = customerService.createCustomer(customerCreateDto);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@RequestBody @Valid CustomerUpdateDto customerDto, @AuthenticationPrincipal AuthenticatedUser user ) {
        CustomerResponseDto result = customerService.updateCustomer(customerDto,user.email());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal AuthenticatedUser user) {
        customerService.deleteCustomer(user.id());
        return ResponseEntity.noContent().build();
    }

}
