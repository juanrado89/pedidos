package rado.alberto.org.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import rado.alberto.org.dto.OrderDto;
import rado.alberto.org.security.AuthenticatedUser;
import rado.alberto.org.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id, @AuthenticationPrincipal AuthenticatedUser user) {
        OrderDto result = orderService.getOrderById(id, user.id());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getOrdersByIdCustomer(@AuthenticationPrincipal AuthenticatedUser user) {
        List<OrderDto> result = orderService.getOrderByIdCustomer(user.id());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/{id}")
    public ResponseEntity<OrderDto> adminGetOrderById(@PathVariable Long id) {
        OrderDto result = orderService.getOrderById(id);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/orders/{idCustomer}")
    public ResponseEntity<List<OrderDto>> adminGetOrderByIdCustomer(@PathVariable Long idCustomer) {
        List<OrderDto> result = orderService.getOrderByIdCustomer(idCustomer);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/")
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDto orderDto, @AuthenticationPrincipal AuthenticatedUser user) {
        OrderDto result = orderService.createOrder(orderDto, user.id());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/")
    public ResponseEntity<OrderDto> updateOrder(@RequestBody @Valid OrderDto orderDto, @AuthenticationPrincipal AuthenticatedUser user) {
        OrderDto result = orderService.updateOrder(orderDto, user.id());
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrderById(@PathVariable Long id, @AuthenticationPrincipal AuthenticatedUser user) {
        orderService.deleteOrder(id, user.id());
        return ResponseEntity.noContent().build();
    }
}
