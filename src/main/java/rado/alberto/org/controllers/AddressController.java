package rado.alberto.org.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rado.alberto.org.dto.AddressCreateDto;
import rado.alberto.org.dto.AddressDto;
import rado.alberto.org.dto.AddressUpdateDto;
import rado.alberto.org.services.AddressService;

import java.util.List;

@RestController()
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable long id) {
        AddressDto result = addressService.getAddressById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all/{idCustomer}")
    public ResponseEntity<List<AddressDto>> getAllAddressesByCustomerId(@PathVariable long idCustomer) {
        List<AddressDto> result = addressService.getAllAddressByCustomerId(idCustomer);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{idCustomer}")
    public ResponseEntity<AddressDto> createAddress(@RequestBody @Valid AddressCreateDto addressDto, @PathVariable long idCustomer) {
        AddressDto result = addressService.createAddress(addressDto, idCustomer);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize(("hasRole('CUSTOMER')"))
    @PutMapping("/")
    public ResponseEntity<AddressDto> updateAddress(@RequestBody @Valid AddressUpdateDto addressDto) {
        AddressDto result = addressService.updateAddress(addressDto);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize(("hasRole('CUSTOMER')"))
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAddress(@PathVariable long id) {
        addressService.deleteAddressById(id);
        return ResponseEntity.noContent().build();
    }

}
