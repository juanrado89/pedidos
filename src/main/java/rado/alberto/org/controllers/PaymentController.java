package rado.alberto.org.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import rado.alberto.org.dto.PaymentDto;
import rado.alberto.org.dto.PaymentStatusDto;
import rado.alberto.org.dto.PaymentUpdateDto;
import rado.alberto.org.security.AuthenticatedUser;
import rado.alberto.org.services.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable Long id, @AuthenticationPrincipal AuthenticatedUser user) {
        PaymentDto result = paymentService.getPaymentById(id, user.id());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/payments")
    public ResponseEntity<List<PaymentDto>> getPaymentsByCustomerId(@AuthenticationPrincipal AuthenticatedUser user) {
        List<PaymentDto> result = paymentService.getPaymentsByCustomerId(user.id());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/{idPayment}")
    public ResponseEntity<PaymentDto> adminGetByPaymentId(@PathVariable Long idPayment) {
        PaymentDto result = paymentService.getPaymentById(idPayment);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/payments/{idCustomer}")
    public ResponseEntity<List<PaymentDto>> adminGetPaymentsByIdCustomer(@PathVariable Long idCustomer) {
        List<PaymentDto> result = paymentService.getPaymentsByCustomerId(idCustomer);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/")
    public ResponseEntity<PaymentDto> createPayment(@RequestBody @Valid PaymentDto paymentDto, @AuthenticationPrincipal AuthenticatedUser user) {
        PaymentDto result = paymentService.createPayment(paymentDto, user.id());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/")
    public ResponseEntity<PaymentUpdateDto> updatePayment(@RequestBody @Valid PaymentUpdateDto paymentDto) {
        PaymentUpdateDto result = paymentService.updatePayment(paymentDto);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/status/{idPayment}")
    public ResponseEntity<PaymentUpdateDto> UpdatePaymentStatus(@PathVariable Long idPayment, @RequestBody @Valid PaymentStatusDto paymentDto) {
        PaymentUpdateDto result = paymentService.updatePaymentStatus(idPayment, paymentDto);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity deletePaymentById(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
