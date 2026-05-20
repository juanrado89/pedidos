package rado.alberto.org.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rado.alberto.org.dto.PaymentDto;
import rado.alberto.org.dto.PaymentUpdateDto;
import rado.alberto.org.services.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable Long id) {
        PaymentDto result = paymentService.getPaymentById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/payments/{idCustomer}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByCustomerId(@PathVariable Long idCustomer) {
        List<PaymentDto> result = paymentService.getPaymentsByCustomerId(idCustomer);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/")
    public ResponseEntity<PaymentDto> createPayment(@RequestBody @Valid PaymentDto paymentDto) {
        PaymentDto result = paymentService.createPayment(paymentDto);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/")
    public ResponseEntity<PaymentUpdateDto> updatePayment(@RequestBody @Valid PaymentUpdateDto paymentDto) {
        PaymentUpdateDto result = paymentService.updatePayment(paymentDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePaymentById(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
