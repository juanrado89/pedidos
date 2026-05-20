package rado.alberto.org.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import rado.alberto.org.dto.AddressCreateDto;
import rado.alberto.org.dto.AddressDto;
import rado.alberto.org.dto.AddressUpdateDto;
import rado.alberto.org.security.AuthenticatedUser;
import rado.alberto.org.services.AddressService;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable long id, @AuthenticationPrincipal AuthenticatedUser user) {
        AddressDto result = addressService.getAddressById(id, user.id());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/all")
    public ResponseEntity<List<AddressDto>> getAllAddressesByCustomerId(@AuthenticationPrincipal AuthenticatedUser user) {
        List<AddressDto> result = addressService.getAllAddressByCustomerId(user.id());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/")
    public ResponseEntity<AddressDto> createAddress(@RequestBody @Valid AddressCreateDto addressDto, @AuthenticationPrincipal AuthenticatedUser user) {
        AddressDto result = addressService.createAddress(addressDto, user.id());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/")
    public ResponseEntity<AddressDto> updateAddress(@RequestBody @Valid AddressUpdateDto addressDto, @AuthenticationPrincipal AuthenticatedUser user) {
        AddressDto result = addressService.updateAddress(addressDto, user.id());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAddress(@PathVariable long id, @AuthenticationPrincipal AuthenticatedUser user) {
        addressService.deleteAddressById(id, user.id());
        return ResponseEntity.noContent().build();
    }

}
